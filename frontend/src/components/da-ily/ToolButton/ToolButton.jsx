import PropTypes from 'prop-types';
import * as S from './ToolButton.styled';

const ToolButton = (props) => {
  const { tool, selected, onSelect } = props;
  return (
    <S.ToolWrapper selected={selected} onClick={onSelect}>
      {tool.icon(S.IconProps(selected))}
    </S.ToolWrapper>
  );
};

ToolButton.propTypes = {
  tool: PropTypes.node,
  selected: PropTypes.bool,
  onSelect: PropTypes.func,
};

export default ToolButton;
