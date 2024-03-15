import PropTypes from 'prop-types';
import * as S from './ToolButton.styled';

const ToolButton = (props) => {
  const { icon, selected, onSelect } = props;
  return (
    <S.ToolWrapper selected={selected} onClick={onSelect}>
      {icon(S.IconProps(selected))}
    </S.ToolWrapper>
  );
};

ToolButton.propTypes = {
  icon: PropTypes.node,
  selected: PropTypes.bool,
  onSelect: PropTypes.func,
};

export default ToolButton;
