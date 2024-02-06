export const dailrys = [
  {
    dailryId: 3,
    title: '오늘의 다일리',
    pages: [
      {
        pageNumber: 1,
        pageId: 1,
        background: '무지',
        thumbnail: '',
        elements: [
          {
            id: 'asd',
            type: 'textBox',
            order: 1,
            position: {
              x: 100,
              y: 50,
            },
            size: {
              width: 200,
              height: 100,
            },
            rotation: '90deg',
            typeContent: {
              backgroundColor: '#ffcc00',
              color: '#333333',
              fontSize: 12,
              text: '아 어지럽다',
              fontWeight: 'bold',
              font: '굴림',
            },
          },
          {
            id: '123adfwr',
            type: 'drawing',
            order: 2,
            position: {
              x: 200,
              y: 300,
            },
            size: {
              width: 200,
              height: 100,
            },
            rotation: '60deg',
            typeContent: {
              base64: 'YXNjc2FzYXZmbnJ0bnJ0bnN0',
            },
          },
          {
            id: '123asd1231',
            type: 'sticker',
            order: 3,
            position: {
              x: 110,
              y: 50,
            },
            size: {
              width: 300,
              height: 150,
            },
            rotation: '45deg',
            typeContent: {
              imageUrl: '',
            },
          },
        ],
      },
      {
        pageNumber: 2,
        pageId: 2,
        background: '무지',
        thumbnail: '',
        elements: [],
      },
      {
        pageNumber: 3,
        pageId: 3,
        background: '무지',
        thumbnail: '',
        elements: [],
      },
    ],
  },
  {
    dailryId: 4,
    title: '내일의 다일리',
    pages: [],
  },
  {
    dailryId: 5,
    title: '어제의 다일리',
    pages: [],
  },
];
