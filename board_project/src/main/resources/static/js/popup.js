document.addEventListener('DOMContentLoaded', () => {

  // 'open-login-popup' ID를 가진 버튼 요소 가져오기
  const openPopupButton = document.getElementById('open-login-popup');
  // 'login-popup' ID를 가진 팝업 오버레이 요소 가져오기
  const popupOverlay = document.getElementById('login-popup');
  // 'popup-close' 클래스를 가진 닫기 버튼 요소 가져오기

  const closePopupButton = popupOverlay ? popupOverlay.querySelector('.popup-close') : null;

  if (openPopupButton && popupOverlay && closePopupButton) {
    // 팝업 열기: 버튼 클릭 시 팝업 오버레이를 화면에 표시 (디스플레이를 'flex'로 변경)
    openPopupButton.addEventListener('click', () => {
      popupOverlay.style.display = 'flex';
    });

    // 팝업 닫기: 닫기 버튼 클릭 시 팝업 오버레이를 화면에서 숨김 (디스플레이를 'none'으로 변경)
    closePopupButton.addEventListener('click', () => {
      popupOverlay.style.display = 'none';
    });

    // 팝업 외부 클릭 시 닫기: 팝업 외부 영역을 클릭하면 팝업 오버레이를 숨김
    window.addEventListener('click', (event) => {
      // 클릭한 위치가 팝업 오버레이 요소라면 팝업을 숨김
      if (event.target === popupOverlay) {
        popupOverlay.style.display = 'none';
      }
    });
  } else {
    console.error('Element(s) not found: Ensure the HTML elements have the correct IDs and classes.');
  }

});
