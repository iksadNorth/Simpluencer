// Null 체크.
function isNotBlank(token) {
    return (token !== null && token !== undefined)
}

// 문자열 빈칸 체크.
function hasSomethingInString(str) {
    return (isNotBlank(str) && str.trim()!=='');
};

// 백엔드 서버 접두사 추가.
function addBaseUrl(url) {
    return `http://localhost:8080${url}`
}

// 쿠키에서 값을 가져오는 함수
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }
}

// 페이지네이션을 위한 쿼리 메서드.
function pageRequest(page, size, sort_field=null, isAsc=true) {
    const query = {
        size: size,
        page: page
    };
    if(sort_field != null) {
        query.sort = [`${sort_field},${isAsc ? 'asc' : 'desc'}`];
    }

    return query;
}

// 파라미터 추가를 위한 메서드.
function addParams(url, params) {
    let url_obj = new URL(addBaseUrl(url));
    Object.keys(params).forEach(key => url_obj.searchParams.append(key, params[key]));
    return url_obj.pathname + url_obj.search;
}

// Content-Type을 form 형식으로
function getHeaderForm() {
    return {
        'Content-Type': 'application/x-www-form-urlencoded',
    };
}

// 날짜 처리를 위한 메서드.
function toDate(isoDateTimeString) {
    return format(new Date(isoDateTimeString), "yy/MM/dd").toString();
};


// 페이지 이동 메서드.
function navigateTo(url) {
    window.location.href = url;
}

// 에러 일괄 처리 메서드.
function doWhenErr(err) {
    alert(err);
}

// 에러 일괄 처리 메서드.
function doWhenInfo(msg) {
    alert(msg);
}

// API 호출 메서드.
function request(url, options) {
  // proxy url 설정.
  url = addBaseUrl(url);

  // options 구조 설정.
  options = {
    ...options,
    headers: {
      ...options.headers
    },
  }

  // csrf 토큰 추가.
  const csrfToken = document.getElementById('_csrf').content;
  const csrfKey = document.getElementById('_csrf_header').content;
  options.headers[csrfKey] = csrfToken;

  // 데이터 형식 추가.
  if(!hasSomethingInString(options.headers['Content-Type']) && !options.noContentType) {
    options.headers['Content-Type'] = 'application/json';
  }

  // 요청 설정 로깅.
  console.log(`[${options.method ?? "GET"}] \n${url}`);
  console.log(`--> 요청 Header:\n`, options);

  // 요청 보내기
  return fetch(url, options)
    .then(response => {
      //// For Ok Checking.
      const responseForLogging = response.clone();

      // Ok 답이 안오면 오류로 넘김.
      // 이 때, 미리 ErrorResponse의 Body에서 errorMessage 필드를 추출하고 throw 함.
      if (!responseForLogging.ok) {
        return responseForLogging.json().then(err => {
          throw new Error(err.errorMessage);
        });
      }

      return response;
    })
    .then(response => {
      //// For Logging Body.
      console.log(`<-- 응답 Header: \n`, response.headers);
      return response;
    })
    .then(response => {
      //// 리다이렉션이면 수동으로 url를 교체함.
      if (response.redirected) {
        window.location.href = response.url;
      } return response;
    })
    .then(response => {
      //// For Convert To Body.
      // [Blocking]여기서 응답 받을 때까지 대기함.
      const contentType = response.headers.get('Content-Type');
      if(contentType && contentType.includes('application/json')) {
        return response.json();
      } else {
        return response.text();
      }
    })
    .then(response => {
      //// For Logging Body.
      console.log(`<-- 응답 Body: \n`, response);
      return response;
    })
    .catch((error) => {
      // 에러 로깅.
      console.error(`??? 에러 발생: \n`, error.message);

      return Promise.reject(error.message);
    });
}

function get(url, options={}) {
  // 메서드 추가.
  options.method = 'GET';
  return request(url, options);
}

function post(url, options={}) {
  // 메서드 추가.
  options.method = 'POST';
  return request(url, options);
  }

function patch(url, options={}) {
  // 메서드 추가.
  options.method = 'PATCH';
  return request(url, options);
}

function del(url, options={}) {
  // 메서드 추가.
  options.method = 'DELETE';
  return request(url, options);
}

function reloadPage() {
  location.reload();
}
