import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import * as S from './Tooltip.styled';

const Tooltip = (props) => {
  const { text, children } = props;
  const [isMouseEntered, setIsMouseEntered] = useState('initial');
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (isMouseEntered === 'leave') {
      setOpen(false);
      return;
    }
    if (isMouseEntered === 'enter') {
      setTimeout(() => {
        setOpen(true);
      }, 500);
    }

    setOpen(false);
  }, [isMouseEntered]);

  return (
    <S.TooltipContainer
      onMouseEnter={() => {
        setIsMouseEntered('enter');
      }}
      onMouseLeave={() => setIsMouseEntered('leave')}
    >
      {children}
      {open && isMouseEntered === 'enter' && <S.Tooltip>{text}</S.Tooltip>}
    </S.TooltipContainer>
  );
};

Tooltip.propTypes = {
  children: PropTypes.node,
  text: PropTypes.string,
};

export default Tooltip;
