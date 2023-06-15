#!/bin/bash

ISSUE_ID=$1
# 현재 위치가 main인지 확인.
# shellcheck disable=SC2006
CURRENT_BRANCH=`git branch --show-current`
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "주의!! 지금 ($CURRENT_BRANCH) 브랜치이므로 main 브랜치로 이동 후 시도하기."
    return
fi

# 이슈 번호가 유효한지 확인.
if [[ -z $ISSUE_ID ]] || [[ $ISSUE_ID == *" "* ]]; then
    echo "주의!! 지금 이슈 번호가 공백이거나 비어있습니다."
    return
fi

# Github Issue URL 생성.
GITHUB_ID="iksadNorth"
GITHUB_PJT="Simpluencer"
ISSUE_URL="https://github.com/${GITHUB_ID}/${GITHUB_PJT}/issues/${ISSUE_ID}"
echo "ISSUE_URL: $ISSUE_URL"

# GitHub 페이지를 호출.
ISSUE_HTML=$(curl -s "$ISSUE_URL")

# 해당 이슈의 제목 추출,
# 태그 제거,
# 제목의 공백을 언더바로 치환,
ISSUE_TITLE=$(\
    echo "$ISSUE_HTML" \
    | grep -oP '<bdi class="js-issue-title markdown-title">(.*)</bdi>' \
    | sed 's/<bdi class="js-issue-title markdown-title">//g' \
    | sed -e 's/<[^>]*>//g' \
    | sed 's/ /_/g' \
)

# git branch 생성
NEW_BRANCH="feature/#${ISSUE_ID}_${ISSUE_TITLE}"
echo "NEW_BRANCH: $NEW_BRANCH"
echo "#${ISSUE_ID} - ${ISSUE_TITLE}"
git checkout -b "$NEW_BRANCH"
