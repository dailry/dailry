import styled from 'styled-components';

export const TextArea = styled.textarea`
  width: 100%;
  height: ${(props) => `${props.height}px`};

  border: 1px solid rgb(116, 171, 217);
  border-radius: 0px;

  outline: none;
  resize: none;
`;
