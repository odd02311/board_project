// path/to/your/javascript-file.js

document.addEventListener('DOMContentLoaded', function() {

    // 푸터를 가져와서 삽입하기
    fetch('footer.html')
        .then(response => response.text()) // 서버에서 받은 응답을 텍스트로 변환
        .then(data => {
            document.getElementById('footer-placeholder').innerHTML = data; // 받은 데이터를 footer-placeholder 요소에 삽입
        })
        .catch(error => {
            console.error('푸터 로딩 오류:', error); // 오류가 발생한 경우 콘솔에 에러 메시지 출력
        });
});
