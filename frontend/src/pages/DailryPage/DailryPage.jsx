// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import Drawing from '../../components/decorate/Drawing/Drawing';
import * as S from './DailryPage.styled';
import dailryData from './dailry.json';
import Button from '../../components/common/Button/Button';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import useCreateDecorateComponent from '../../hooks/useCreateDecorateComponent';
import { CursorIcon, DrawIcon, TextIcon, StickerIcon } from '../../assets/svg';

const TextBox = () => <div>ff</div>;

const DecorateComponents = {
  drawing: {
    type: 'drawing',
    component: <Drawing />,
  },
  textbox: {
    component: <TextBox />,
  },
};

const DailryPage = () => {
  const { elements } = dailryData;
  const [target, setTarget] = useState(null);
  const moveableRef = useRef([]);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const tools = [
    { icon: (props) => <CursorIcon {...props} /> },
    { icon: (props) => <DrawIcon {...props} /> },
    { icon: (props) => <TextIcon {...props} /> },
    { icon: (props) => <StickerIcon {...props} /> },
  ];
  const [selectedTool, setSelectedTool] = useState(null);

  const { setNewDecorateComponentPosition, createNewDecorateComponent } =
    useCreateDecorateComponent(setDecorateComponents);

  useEffect(() => {
    if (elements) setDecorateComponents(elements);
  }, [elements]);

  const handleMouseDown = (e, index) => {
    setTarget(index + 1);
  };

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper onClick={(e) => setNewDecorateComponentPosition(e)}>
        {decorateComponents.map((element, index) => {
          const { id, type, position, properties } = element;
          return (
            <div
              key={id}
              onMouseDown={(e) => handleMouseDown(e, index)}
              style={S.ElementStyle({ position, properties })}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
            >
              {DecorateComponents[type].component}
            </div>
          );
        })}
        {target && <Moveable target={moveableRef[target]} />}
      </S.CanvasWrapper>
      <S.SideWrapper>
        <S.ToolWrapper>
          {tools.map((tool, index) => {
            return (
              <ToolButton
                key={index}
                tool={tool}
                selected={selectedTool === index}
                onSelect={() => setSelectedTool(index)}
              />
            );
          })}
          {/* <Button onClick={() => createNewDecorateComponent('drawing')}> */}
          {/*  드로잉 */}
          {/* </Button> */}
          {/* <Button onClick={() => createNewDecorateComponent('textbox')}> */}
          {/*  텍스트 */}
          {/* </Button> */}
        </S.ToolWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
