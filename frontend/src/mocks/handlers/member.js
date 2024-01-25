import { http, HttpResponse } from 'msw';
import memberData from '../datas/member.json';

export const memberHandlers = [
  http.get('/members', () => {
    return HttpResponse.json(memberData);
  }),
];
