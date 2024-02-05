import { useState, useEffect } from 'react';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import NavigationInput from '../NavigationItem/NavigationInput';
import { NavigationItemIcon } from '../../../assets/svg';
import { getDailry, postDailry } from '../../../apis/dailryApi';
import { useDailryContext } from '../../../hooks/useDailryContext';
import DailryHamburger from '../Hamburger/DailryHamburger';

const DailryNavigation = () => {
  const [dailryItem, setDailryItem] = useState([]);
  const [editingDailry, setEditingDailry] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  useEffect(() => {
    getDailry().then((response) => setDailryItem(response.data));
  }, [editingDailry]);

  const handleItemClick = (dailryId) => {
    setCurrentDailry({ dailryId, pageId: 1 });
  };

  const isCurrent = (id) => {
    return currentDailry.dailryId === id;
  };

  const handleAddClick = () => {
    postDailry({ title: '새 다일리' }).then((res) => {
      const { id } = res.data;
      setEditingDailry(id);
    });
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      {dailryItem.map(({ id, title }) => {
        if (editingDailry === id) {
          return (
            <NavigationInput
              key={id}
              dailryId={id}
              title={title}
              setEditingDailry={setEditingDailry}
            ></NavigationInput>
          );
        }
        return (
          <NavigationItem
            key={id}
            onClick={() => handleItemClick(id)}
            current={isCurrent(id)}
            icon={<NavigationItemIcon />}
            hamburger={
              <DailryHamburger
                setEditingDailry={setEditingDailry}
                dailryId={id}
              />
            }
          >
            {title}
          </NavigationItem>
        );
      })}
      <S.AddDailry onClick={handleAddClick}>+ 새 다일리 만들기</S.AddDailry>
    </S.NavigationWrapper>
  );
};

export default DailryNavigation;
