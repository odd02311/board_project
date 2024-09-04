Highcharts.chart("container", {
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
    packedbubble: {
      minSize: "30%",
      maxSize: "100%",
      zMin: 0,
      zMax: 1000,
      layoutAlgorithm: {
        splitSeries: true,
        gravitationalConstant: 0.01,
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
  series: [
    {
      name: "Europe",
      data: [
        {
          name: "Germany",
          value: 673.6,
        },
        {
          name: "Croatia",
          value: 5000.2,
        },
        {
          name: "Belgium",
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
      name: "Africa",
      data: [
        {
          name: "Senegal",
          value: 12.1,
        },
        {
          name: "Cameroon",
          value: 10.1,
        },
        {
          name: "Zimbabwe",
          value: 10.2,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      name: "Oceania",
      data: [
        {
          name: "Australia",
          value: 393.2,
        },
        {
          name: "New Zealand",
          value: 32.4,
        },
        {
          name: "Papua New Guinea",
          value: 4.7,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      name: "North America",
      data: [
        {
          name: "Costa Rica",
          value: 8.6,
        },
        {
          name: "Honduras",
          value: 10.6,
        },
        {
          name: "Jamaica",
          value: 6.1,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      name: "South America",
      data: [
        {
          name: "El Salvador",
          value: 8.0,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
    {
      name: "Asia",
      data: [
        {
          name: "Nepal",
          value: 15.8,
        },
        {
          name: "Georgia",
          value: 12.0,
        },
      ],
      color: "rgba(255,255,255,0)",
    },
  ],
});
