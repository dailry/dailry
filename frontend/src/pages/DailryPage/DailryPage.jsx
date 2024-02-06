// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import useCreateDecorateComponent from '../../hooks/useCreateDecorateComponent/useCreateDecorateComponent';
import { TOOLS } from '../../constants/toolbar';
import {
  DECORATE_COMPONENT,
  DECORATE_TYPE,
} from '../../constants/decorateComponent';
import { LeftArrowIcon, RightArrowIcon } from '../../assets/svg';
import Text from '../../components/common/Text/Text';
import { useDailryContext } from '../../hooks/useDailryContext';
import { postPage, getPages } from '../../apis/dailryApi';

const DailryPage = () => {
  const parentRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const [selectedTool, setSelectedTool] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  const { createNewDecorateComponent } = useCreateDecorateComponent(
    decorateComponents,
    setDecorateComponents,
    parentRef,
  );

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  const { dailryId, pageId } = currentDailry;

  const handleLeftArrowClick = () => {
    if (pageId === 1) {
      console.log('첫 번째 페이지입니다');
      return;
    }
    setCurrentDailry({ dailryId, pageId: pageId - 1 });
  };

  const handleRightArrowClick = async () => {
    getPages(dailryId).then((response) => {
      if (response.data.pages.length === pageId) {
        console.log('마지막 페이지입니다');
        return;
      }
      setCurrentDailry({ dailryId, pageId: pageId + 1 });
    });
  };

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper
        ref={parentRef}
        onClick={(e) => createNewDecorateComponent(e, selectedTool)}
      >
        {decorateComponents.map((element, index) => {
          const { id, type, position, properties, order, size } = element;
          return (
            <div
              key={id}
              onMouseDown={() => setTarget(index + 1)}
              style={S.ElementStyle({ position, properties, order, size })}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
            >
              {DECORATE_COMPONENT[type]}
            </div>
          );
        })}

        {isMoveable() && <Moveable target={moveableRef[target]} />}
      </S.CanvasWrapper>
      <S.SideWrapper>
        <S.ToolWrapper>
          {TOOLS.map(({ icon, type }, index) => {
            const onSelect = (t) => {
              if (t === 'page') {
                postPage(dailryId).then((response) =>
                  setCurrentDailry({ dailryId, pageId: response.data.pageId }),
                );
                setTimeout(() => setSelectedTool(null), 150);
              }
              setSelectedTool(selectedTool === t ? null : t);
            };
            return (
              <ToolButton
                key={index}
                icon={icon}
                selected={selectedTool === type}
                onSelect={() => onSelect(type)}
              />
            );
          })}
        </S.ToolWrapper>
        <S.ArrowWrapper>
          <S.ArrowButton direction={'left'} onClick={handleLeftArrowClick}>
            <LeftArrowIcon />
          </S.ArrowButton>
          <Text bold color={'#ffffff'} size={24}>
            {pageId}
          </Text>
          <S.ArrowButton direction={'right'} onClick={handleRightArrowClick}>
            <RightArrowIcon />
          </S.ArrowButton>
        </S.ArrowWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
