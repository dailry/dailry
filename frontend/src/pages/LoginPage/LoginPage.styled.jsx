import { css, styled } from 'styled-components';
import { Link } from 'react-router-dom';
import { TEXT, LOGIN } from '../../styles/color';

export const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 25px;
`;

export const FormWrapper = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 8px;
`;

export const LinkContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 8px;
`;

export const LinkWrapper = styled(Link)`
  color: ${TEXT.purple};
  font-size: 12px;
`;

export const LineWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const Line = styled.hr`
  display: inline;
  margin: 10px;
  width: 100px;
  height: 1px;
  border: 1px solid ${TEXT.line};
`;

export const SocialLoginWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;

  width: 100%;
`;

export const GoogleLoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  padding: 0 8px;

  width: 110px;
  height: 40px;
  border: thin solid ${LOGIN.googleBorder};
  border-radius: 5px;
  box-shadow: 1px 1px 1px ${LOGIN.googleBorder};
`;

export const Logo = styled.img`
  width: 18px;
  height: 18px;
`;

export const GoogleLoginText = css`
  font-size: 14px;
  font-weight: bold;
  color: ${LOGIN.googleText};

  text-align: center;
`;

export const KakaoLoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  padding: 0 8px;

  width: 110px;
  height: 40px;

  border-radius: 5px;
  background-color: ${LOGIN.kakaoBackground};
`;

export const KakaoLoginText = css`
  font-size: 14px;
  font-weight: 800;
  color: ${LOGIN.kakaoText};
`;
