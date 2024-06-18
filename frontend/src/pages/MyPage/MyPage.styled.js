import { styled } from 'styled-components';
import { BACKGROUND, COMMUNITY } from '../../styles/color';

export const MyPageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  padding: 16px;
  width: 100%;
`;

export const NameArea = styled.div`
  padding: 12px;
`;

export const RowWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 8px;
  justify-content: start;
  align-items: center;
`;

export const UserInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 30px;
  width: 70%;
  border-radius: 8px;
  background-color: ${BACKGROUND.bright};

  padding: 30px;
`;

export const LabelWrapper = styled.div`
  width: 120px;
`;

export const FormWrapper = styled.form`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

export const ActivityNameWrapper = styled.div`
  display: flex;
  flex-direction: row;
`;

export const ActivityNameButton = styled.button`
  margin: 12px 0 0 0;
  width: 35%;
  height: 35px;
  border-radius: 8px 8px 0 0;
  background-color: ${({ current }) =>
    current ? BACKGROUND.bright : BACKGROUND.transparent1};
`;

export const UserActivityWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: -8px 0 0 0;
  width: 70%;
  border-radius: 0 0 8px 8px;
  background-color: ${BACKGROUND.bright};
`;

export const UserPostsArea = styled.div`
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 12px;
  padding: 8px 30px 8px 12px;

  height: 80px;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1px;
    background-color: ${COMMUNITY.line};
  }

  &:last-of-type::after {
    display: none;
  }
`;

export const PostImageWrapper = styled.img`
  height: 100%;
  border: 1px solid ${COMMUNITY.line};
  border-radius: 4px;
`;

export const PostContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 8px;
  flex: 1;

  height: 100%;
`;

export const LikeWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 40px;
  font-size: 12px;
`;

export const UserCommentArea = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 8px;

  padding: 12px;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1px;
    background-color: ${COMMUNITY.line};
  }

  &:last-of-type::after {
    display: none;
  }
`;

export const PageButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 8px;

  width: 70%;
`;

export const PageButton = styled.button`
  width: 23px;
  height: 25px;
  border-radius: 4px;
  background-color: ${BACKGROUND.bright};
`;

export const WithdrawalWrapper = styled.div`
  display: flex;
  justify-content: end;

  padding: 8px;
  width: 70%;
`;

export const WithdrawalButton = styled.button`
  font-size: 12px;
  width: auto;
`;
