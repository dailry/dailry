import { http, HttpResponse } from 'msw';
import { dailrys } from '../datas/dailry';

let dailryData = [...dailrys];
let nextDailryId = 6;
let nextPageId = 4;

export const dailryHandlers = [
  http.post('https://api.da-ily.site/api/dailry', async ({ request }) => {
    const { title } = await request.json();
    const newDailry = { dailryId: nextDailryId, title, pages: [] };
    nextDailryId += 1;
    dailryData.push(newDailry);
    return HttpResponse.json({
      dailryId: newDailry.dailryId,
      title: newDailry.title,
    });
  }),

  http.patch(
    'https://api.da-ily.site/api/dailry/:dailryId',
    async ({ params, request }) => {
      const dailryId = Number(params.dailryId);
      const { title } = await request.json();
      dailryData.forEach((dailry, index) => {
        if (dailry.dailryId === dailryId) {
          dailryData[index].title = title;
        }
      });
      return HttpResponse.json({ dailryId, title });
    },
  ),

  http.get('https://api.da-ily.site/api/dailry/:dailryId', ({ params }) => {
    const dailryId = Number(params.dailryId);
    const foundDailry = dailryData.find((item) => item.dailryId === dailryId);

    if (foundDailry) {
      return HttpResponse.json({
        dailryId: foundDailry.dailryId,
        title: foundDailry.title,
      });
    }
    return new HttpResponse(null, { status: 500 });
  }),

  http.get('https://api.da-ily.site/api/dailry', () => {
    const tmpDailry = dailryData.map(({ dailryId, title }) => {
      return { dailryId, title };
    });
    return HttpResponse.json(tmpDailry);
  }),

  http.delete('https://api.da-ily.site/api/dailry/:dailryId', ({ params }) => {
    const dailryId = Number(params.dailryId);
    dailryData = dailryData.filter((data) => data.dailryId !== dailryId);
    return HttpResponse.json({
      statusCode: 200,
      successful: true,
    });
  }),

  http.post(
    'https://api.da-ily.site/api/dailry/:dailryId/pages',
    ({ params }) => {
      const dailryId = Number(params.dailryId);
      const newPage = {
        pageId: nextPageId,
        pageNumber: 1,
        background: 'grid',
        thumbnail: '',
        elements: [],
      };
      nextPageId += 1;
      dailryData.forEach((dailry, index) => {
        if (dailry.dailryId === dailryId) {
          const pageNumber = dailry.pages.length + 1;
          dailryData[index].pages.push({ pageNumber, ...newPage });
        }
      });
      return HttpResponse.json({
        pageId: newPage.pageId,
        pageNumber: newPage.pageNumber,
        background: newPage.background,
      });
    },
  ),

  http.post(
    'https://api.da-ily.site/api/dailry/pages/:pageId/edit',
    async ({ params, request }) => {
      const pageId = Number(params.pageId);
      const { thumbnail, dailryPageRequest } = await request.json();
    },
  ),

  http.get('https://api.da-ily.site/api/dailry/pages/:pageId', ({ params }) => {
    const { pageId } = Number(params.pageId);
    const foundPage = dailryData.reduce((acc, cur) => {
      if (cur.pages.pageId === pageId) {
        return cur.pages;
      }
      return acc;
    }, null);
    if (foundPage) {
      return HttpResponse.json(foundPage);
    }
    return new HttpResponse(null, { status: 500 });
  }),

  http.get(
    'https://api.da-ily.site/api/dailry/:dailryId/pages',
    ({ params }) => {
      const dailryId = Number(params.dailryId);
      const foundDailry = dailryData.find((item) => item.dailryId === dailryId);
      const newPages = foundDailry.pages.map(
        ({ pageNumber, pageId, thumbnail }) => {
          return { pageNumber, pageId, thumbnail };
        },
      );
      return HttpResponse.json({ dailryId, pages: newPages });
    },
  ),
];
