import { setupWorker } from 'msw/browser';
import { http } from 'msw';
import { authHandlers } from './handlers/auth';
import { commentsHandlers } from './handlers/comments';
import { dailryHandlers } from './handlers/dailry';
import { memberHandlers } from './handlers/member';
import { postHandlers } from './handlers/posts';

const handlers = [
  ...authHandlers,
  ...commentsHandlers,
  ...dailryHandlers,
  ...memberHandlers,
  ...postHandlers,
];

export const worker = setupWorker(...handlers);
