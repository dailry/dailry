import { styled } from 'styled-components';

export const RegisterWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 8px;

  & > :first-child {
    margin-bottom: 10px;
  }

  & > :last-child {
    margin-top: 4px;
  }
`;
