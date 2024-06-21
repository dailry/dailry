import { getHotPosts, getPosts, getPostsByHashtags } from '../apis/postApi';

export const POSTS_LOAD_CONDITIONS = [
  {
    parameter: 'hashtag',
    check: (val) => val !== null,
    getPosts: (param) => getPostsByHashtags(param),
    post: 'posts',
  },
  {
    parameter: 'orderBy',
    check: (val) => val === 'latest',
    getPosts: (param) => getPosts(param),
    post: 'posts',
  },
  {
    parameter: 'orderBy',
    check: (val) => val === 'hotPosts',
    getPosts: (param) => getHotPosts(param),
    post: 'hotPosts',
  },
];
