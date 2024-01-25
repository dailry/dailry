import { http, HttpResponse } from 'msw';

const dailryData = [
  { id: 1, title: 'bird dailry' },
  { id: 3, title: '안녕나는다일리' },
];

export const dailryHandlers = [
  http.get('/dailry', () => {
    return HttpResponse.json(dailryData);
  }),
];
