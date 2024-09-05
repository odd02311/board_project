// 해시태그와 카테고리 맵핑 설정
const tagToCategory = {
  Html: 'frontend',
  Css: 'frontend',
  Js: 'frontend',
  Java: 'backend',
  Spring: 'backend',
  Flask: 'backend',
  '.Net': 'csharp',
  MaUI: 'csharp',
  WPF: 'csharp',
};

const chart = Highcharts.chart("container", {
  chart: {
    type: "packedbubble",
    height: "50%",
  },
  title: {
    text: "HashTag",
    align: "center",
  },
  subtitle: {
    text: "search with hashtag",
    align: "center",
  },

  tooltip: {
    enabled: false,
  },

  plotOptions: {
    series: {
      cursor: "pointer",
      point: {
        events: {
          click: function () {
            const hashtag = this.name;
            // 해시태그에 해당하는 카테고리 검색
            const category = tagToCategory[hashtag];
            if (category) {
              // 카테고리 값으로 서버로 이동
              window.location.href = '/articles/search-hashtag?searchValue=' + category;
            } else {
              console.log('해시태그에 해당하는 카테고리를 찾을 수 없습니다.');
            }
          },
        },
      },
    },
    packedbubble: {
      minSize: "100%",
      maxSize: "150%",
      zMin: 0,
      zMax: 1000,
      layoutAlgorithm: {
        splitSeries: true,
        gravitationalConstant: 0.03,
      },
      dataLabels: {
        enabled: true,
        format: "{point.name}",
        style: {
          color: "black",
          textOutline: "none",
          fontWeight: "normal",
        },
      },
    },
  },

  series: [
    {
      allowPointSelect: true,
      name: "frontend",
      data: [
        { name: "Html", value: 10 },
        { name: "Css", value: 9 },
        { name: "Js", value: 8 },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      allowPointSelect: true,
      name: "backend",
      data: [
        { name: "Java", value: 10 },
        { name: "Spring", value: 9 },
        { name: "Flask", value: 8 },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      allowPointSelect: true,
      name: "csharp",
      data: [
        { name: ".Net", value: 7 },
        { name: "MaUI", value: 6 },
        { name: "WPF", value: 5 },
      ],
      color: "rgba(255,255,255,0)",
    },
  ],
});

document.getElementById("button").addEventListener("click", () => {
  const selectedPoints = chart.getSelectedPoints();

  if (selectedPoints.length > 0) {
    console.log(selectedPoints[0].name);
  } else {
    console.log("No point selected");
  }
});