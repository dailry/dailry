import { http, HttpResponse } from 'msw';
import { dailrys } from '../datas/dailry';

let dailryData = [...dailrys];

export const dailryHandlers = [
  http.get('https://api.da-ily.site/api/dailry', () => {
    return HttpResponse.json(dailryData);
  }),

  http.get('https://api.da-ily.site/api/dailry/:dailryId', ({ params }) => {
    const dailryId = Number(params.dailryId);
    const foundDailry = dailryData.find((item) => item.dailryId === dailryId);

    if (foundDailry) {
      return HttpResponse.json(foundDailry);
    }
    return new HttpResponse(null, { status: 500 });
  }),

  http.post('https://api.da-ily.site/api/dailry', async ({ request }) => {
    const { title } = await request.json();
    const nextId = dailryData[dailryData.length - 1].dailryId + 1;
    const newDailry = { dailryId: nextId, title };
    dailryData.push(newDailry);
    return HttpResponse.json(newDailry);
  }),

  http.delete('https://api.da-ily.site/api/dailry/:dailryId', ({ params }) => {
    const dailryId = Number(params.dailryId);
    dailryData = dailryData.filter((data) => data.dailryId !== dailryId);
    return HttpResponse.json({
      statusCode: 200,
      successful: true,
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
];
