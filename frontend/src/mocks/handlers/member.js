import { http, HttpResponse } from 'msw';
import { memberData } from '../datas/member';

export const memberHandlers = [
  http.get('https://api.da-ily.site/api/member', () => {
    return HttpResponse.json(memberData[0]);
  }),
];
