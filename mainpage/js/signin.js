document.addEventListener('DOMContentLoaded', () => {
    const dropdownButton = document.getElementById('occupation-btn');
    const dropdownContent = document.getElementById('occupation-dropdown');
    const arrowIcon = document.querySelector('.arrow');

    // 화살표 아이콘 클릭 시 드롭다운 내용 토글
    arrowIcon.addEventListener('click', (event) => {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        const isVisible = dropdownContent.style.display === 'block';
        dropdownContent.style.display = isVisible ? 'none' : 'block';
    });

    // 드롭다운 항목 클릭 시 선택된 값으로 버튼 텍스트 변경
    dropdownContent.addEventListener('click', (event) => {
        if (event.target.tagName === 'A') {
            dropdownButton.firstChild.textContent = event.target.textContent; // 버튼 텍스트 변경
            dropdownContent.style.display = 'none'; // 클릭 시 드롭다운 닫기
        }
    });

    // 클릭 외부 영역 클릭 시 드롭다운 닫기
    document.addEventListener('click', (event) => {
        if (!event.target.closest('.dropdown')) {
            if (dropdownContent.style.display === 'block') {
                dropdownContent.style.display = 'none';
            }
        }
    });
});