let isEditing = false;

function onClickEdit(clickedBtn) {
  const rootEl = clickedBtn.parentElement.parentElement.nextElementSibling;

  const targetText = rootEl.firstElementChild;
  const innerText = targetText.innerText;

  const InputField = rootEl.lastElementChild;

  editComment(clickedBtn, targetText, innerText, InputField);
}

function editComment(clickedBtn, targetText, innerText, InputField) {
  if (!isEditing) {
    clickedBtn.innerText = "완료";

    targetText.style.display = "none";
    InputField.style.display = "unset";
    InputField.defaultValue = innerText;
    isEditing = true;
  } else if (isEditing) {
    clickedBtn.innerText = "수정";

    targetText.innerText = InputField.value;

    InputField.style.display = "none";
    targetText.style.display = "unset";
    isEditing = false;
  }
}
