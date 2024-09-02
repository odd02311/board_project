function appendRow() {
  row = getRow();
  document.querySelector(".table").append(row);
}

function getRow() {
  const row = document.createElement("tbody");
  row.setAttribute("class", "row-box outer-box");
  row.innerHTML = `      
            <tr>

              <td class="data no">sample</td >
              <td class="data id">sample</td >
              <td class="data title">sampleText</td >
              <td class="data date">sampleText</td >
              <td class="data views">sampleText</td >
            </tr>
          `;

  return row;
}

function appendHeadRow() {
  const row = document.createElement("table");
  row.innerHTML = ` 
    <thead>
      <tr>
        <th class="data no">sample</th>
        <th class="data id">sample</th>
        <th class="data title">sampleText</th>
        <th class="data date">sampleText</th>
        <th class="data views">sampleText</th>
      </tr>
    </thead>
    
`;

  console.log(row.innerHTML);

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
