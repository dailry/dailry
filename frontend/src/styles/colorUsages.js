import { COLORS } from './colors';

export const BACKGROUND = Object.freeze({
  top: COLORS.purple2,
  bottom: COLORS.blue6,
});
export const TEXT = Object.freeze({
  logo: COLORS.blue1,
  normal: COLORS.black,
  reversed: COLORS.white,
  colored: COLORS.purple3,
  placeHolder: COLORS.gray1,
  disabled: COLORS.gray4,
  error: COLORS.red1,
  valid: COLORS.green1,
});

// 아래에 페이지별 or 비슷한 스타일의 페이지 모아서 객체 만들기
// 공통으로 쓸만하면 위에 추가해도 됨
// e.g.
// export const LOGIN = { container, button, input, etc.. }
