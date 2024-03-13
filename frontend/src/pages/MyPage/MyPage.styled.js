import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';

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
