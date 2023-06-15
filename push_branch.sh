#!/bin/bash

# shellcheck disable=SC2006
CURRENT_BRANCH=`git branch --show-current`
REMOTE_REPO="origin"
PUSH_TO="main"

if [ "$CURRENT_BRANCH" = "$PUSH_TO" ]; then
    echo "주의!! 지금 ($CURRENT_BRANCH) 브랜치이므로 새 브랜치 생성 후 커밋하기"
    return
fi

echo "git push"
git pull $REMOTE_REPO $PUSH_TO

echo "현재 브랜치 ($CURRENT_BRANCH)로 push"
git push $REMOTE_REPO "$CURRENT_BRANCH"

echo "main으로 체크 아웃"
git checkout $PUSH_TO
