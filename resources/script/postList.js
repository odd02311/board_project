function appendRow() {
  row = getRow();
  document.querySelector(".table").append(row);
}

function getRow() {
  const row = document.createElement("div");
  row.setAttribute("class", "row-box outer-box");
  row.innerHTML = `      
            <div class="left-value row-box">
              <div class="data no">sample</div>
              <div class="data id">sample</div>
              <div class="data title">sampleText</div>
            </div>
            <div class="right-value row-box">
              <div class="data date">sampleText</div>
              <div class="data views">sampleText</div>
            </div>
          `;

  return row;
}

function appendHeadRow() {
  row = getRow();
  row.setAttribute("class", "row-box head-row outer-box");
  document.querySelector(".table").append(row);
  row.style.borderWidth = "1px 0px 1px 0px";
}

window.onload = () => {
  appendHeadRow();
  for (let i = 0; i < 20; i++) {
    appendRow();

    console.log("append");
  }
};
