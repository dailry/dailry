import { styled } from 'styled-components';

export const TextWrapper = styled.p`
  font-size: ${({ size }) => size};
  font-weight: ${({ weight }) => weight};
  color: ${({ color }) => color};
  && {
    ${(props) => props.css}
  }
`;
