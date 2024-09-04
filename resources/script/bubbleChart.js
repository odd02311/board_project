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
  //   tooltip: {
  //     useHTML: true,
  //     pointFormat: "<b>{point.name}:</b> {point.value}m CO<sub>2</sub>",
  //   },
  plotOptions: {
    // series: {
    //   animation: false,
    // },
    packedbubble: {
      minSize: "40%",
      maxSize: "100%",
      zMin: 0,
      zMax: 1000,
      layoutAlgorithm: {
        splitSeries: true,
        gravitationalConstant: 0.03,
      },
      dataLabels: {
        enabled: true,
        format: "{point.name}",
        /*               filter: {
                            property: 'y',
                            operator: '>',
                            value: 250
                        }, */
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
          value: 673.6,
        },
        {
          name: "Css",
          value: 5000.2,
        },
        {
          name: "Js",
          value: 90.4,
          events: {
            click: function () {
              console.log("clicked b");
            },
          },
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
          value: 12.1,
        },
        {
          name: "Spring",
          value: 11,
        },
        {
          name: "Flask",
          value: 10.2,
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
          value: 393.2,
        },
        {
          name: "MaUI",
          value: 32.4,
        },
        {
          name: "WPF",
          value: 4.7,
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
