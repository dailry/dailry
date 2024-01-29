import { http, HttpResponse } from 'msw';
import dailryData from '../datas/dailry.json';

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
];
