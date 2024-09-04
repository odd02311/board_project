// https://api.highcharts.com/highcharts/tooltip
// 차트 매뉴얼
//

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
  //   tooltip: {
  //     useHTML: true,
  //     pointFormat: "<b>{point.name}:</b> {point.value}m CO<sub>2</sub>",
  //   },
  plotOptions: {
    // series: {
    //   animation: false,
    // },
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
        format:
          "{point.name}" /*filter: {property: 'y', operator: '>', value: 250}, 값 필터 걸수 있음!*/,
        style: {
          color: "black",
          textOutline: "none",
          fontWeight: "normal",
        },
      },
    },
  },
  //   series: {
  //     animation: { duration: 100 },
  //     allowPointSelect: true,
  //   },
  series: [
    {
      allowPointSelect: true,
      name: "fornt-end",
      data: [
        {
          name: "Html",
          value: 10,
        },
        {
          name: "Css",
          value: 9,
        },
        {
          name: "Js",
          value: 8,
          //   events: { // 클릭이벤트 허용 가능함
          //     click: function () {
          //       console.log("clicked b");
          //     },
          //   },
        },
      ],
      color: "rgba(255,255,255,0)",
      events: {
        click: function () {
          console.log("clicked");
        },
      },
    },
    {
      allowPointSelect: true,
      name: "Back-end",
      data: [
        {
          name: "Java",
          value: 10,
        },
        {
          name: "Spring",
          value: 9,
        },
        {
          name: "Flask",
          value: 8,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      allowPointSelect: true,
      name: "CSharp",
      data: [
        {
          name: ".Net",
          value: 7,
        },
        {
          name: "MaUI",
          value: 6,
        },
        {
          name: "WPF",
          value: 5,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
  ],
});

document.getElementById("button").addEventListener("click", () => {
  const selectedPoints = chart.getSelectedPoints();

  console.log(selectedPoints[0].name);
});