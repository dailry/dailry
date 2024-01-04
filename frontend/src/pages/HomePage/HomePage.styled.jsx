import { css, styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { HOME, BACKGROUND, TEXT } from '../../styles/color';
import { RingIcon } from '../../assets/svg';

export const BackGround = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100vw;
  height: 100dvh;

  background-color: ${HOME.backGround};
`;

export const HomeWrapper = styled.div`
  position: relative;

  aspect-ratio: 1.1/1;
  height: 100%;
`;

export const RingWrapper = styled(RingIcon)`
  position: absolute;
  left: -61px;
  top: ${({ top }) => top};
  z-index: 3;

  width: 118px;
  height: 62px;
`;

export const FrontCover = styled.div`
  display: flex;
  justify-content: center;

  position: absolute;
  left: 0;
  top: 0;
  z-index: 2;

  aspect-ratio: 1;
  height: calc(100% - 10px);
  padding-top: 130px;

  background: ${BACKGROUND.gradient};

  border-radius: 20px;

  box-shadow: 0 4px 4px #00000040;
`;

export const BackCover = styled.div`
  position: absolute;
  left: 12px;
  top: 20px;

  aspect-ratio: 1.02/1;
  height: calc(100% - 20px);

  background: ${BACKGROUND.darkGradient};

  border-radius: 20px;

  box-shadow: 0 4px 4px #00000040;
`;

export const BookMark1 = css`
  left: 670px;
  top: 142px;

  padding-right: 30px;

  background-color: ${HOME.menu1};

  transform: rotate(-2.92deg);
`;

export const BookMark2 = css`
  left: 692px;
  top: 208px;

  padding-right: 20px;

  background-color: ${HOME.menu2};

  transform: rotate(0.59deg);
`;

export const BookMark3 = css`
  left: 711px;
  top: 328px;

  padding-right: 15px;

  background-color: ${HOME.menu3};

  transform: rotate(-0.05deg);
`;

export const BookMark4 = css`
  left: 671px;
  top: 642px;

  padding-right: 20px;

  background-color: ${HOME.menu4};

  transform: rotate(5.46deg);
`;

export const BookMark = styled(Link)`
  position: absolute;
  z-index: 1;
  text-align: end;
  line-height: 60px;

  width: 225px;
  height: 60px;

  font-size: 25px;
  color: ${TEXT.black};

  box-shadow: 0 4px 4px #00000040;

  && {
    ${(props) => props.css}
  }
`;
