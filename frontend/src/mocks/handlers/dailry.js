import { http, HttpResponse } from 'msw';
import { dailryData } from '../datas/dailry';

export const dailryHandlers = [
  http.get('https://api.da-ily.site/api/dailry', () => {
    return HttpResponse.json(dailryData);
  }),

  http.get('https://api.da-ily.site/api/dailry/:id', ({ params }) => {
    const dailryId = Number(params.id);
    const foundDailry = dailryData.find((item) => item.id === dailryId);

    if (foundDailry) {
      return HttpResponse.json(foundDailry);
    }
    return new HttpResponse(null, { status: 500 });
  }),

  http.post('https://api.da-ily.site/api/dailry', async ({ request }) => {
    const { title } = await request.json();
    const nextId = dailryData[dailryData.length - 1].id + 1;
    const newDailry = { id: nextId, title };
    dailryData.push(newDailry);
    return HttpResponse.json(newDailry);
  }),
];
