import { http, HttpResponse } from 'msw';
import memberData from '../datas/member.json';

export const memberHandlers = [
  http.get('https://api.da-ily.site/api/member', () => {
    return HttpResponse.json(memberData[0]);
  }),
];
