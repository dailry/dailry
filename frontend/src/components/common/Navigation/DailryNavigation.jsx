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
  const [dailryItems, setDailryItems] = useState([]);
  const [editingDailry, setEditingDailry] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  useEffect(() => {
    getDailry().then((response) => setDailryItems(response.data));
  }, [editingDailry]);

  const handleItemClick = (dailryId) => {
    setCurrentDailry({ dailryId, pageId: 1 });
  };

  const isCurrent = (id) => {
    return currentDailry.dailryId === id;
  };

  const handleAddClick = () => {
    postDailry({ title: '새 다일리' }).then((res) => {
      const { dailryId } = res.data;
      setEditingDailry(dailryId);
    });
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      {dailryItems.map(({ dailryId, title }) => {
        if (editingDailry === dailryId) {
          return (
            <NavigationInput
              key={dailryId}
              dailryId={dailryId}
              title={title}
              setEditingDailry={setEditingDailry}
            />
          );
        }
        return (
          <NavigationItem
            key={dailryId}
            onClick={() => handleItemClick(dailryId)}
            current={isCurrent(dailryId)}
            icon={<NavigationItemIcon />}
            hamburger={
              <DailryHamburger
                setEditingDailry={setEditingDailry}
                dailryId={dailryId}
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
