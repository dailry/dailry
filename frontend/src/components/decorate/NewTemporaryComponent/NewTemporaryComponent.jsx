import PropTypes from 'prop-types';
import { DECORATE_COMPONENT } from '../../../constants/decorateComponent';
import * as S from '../../../pages/DailryPage/DailryPage.styled';

const NewTemporaryComponent = (props) => {
  const { newDecorateComponent, canEditDecorateComponentId } = props;
  return (
    newDecorateComponent && (
      <div
        onMouseDown={(e) => {
          e.stopPropagation();
        }}
        style={S.ElementStyle({
          position: newDecorateComponent.position,
          properties: newDecorateComponent.properties,
          order: newDecorateComponent.order,
          size: newDecorateComponent.size,
          canEdit: canEditDecorateComponentId === newDecorateComponent.id,
        })}
      >
        {DECORATE_COMPONENT[newDecorateComponent.type]}
      </div>
    )
  );
};

export default NewTemporaryComponent;

NewTemporaryComponent.propTypes = {
  newDecorateComponent: PropTypes.object,
  canEditDecorateComponentId: PropTypes.string,
};
