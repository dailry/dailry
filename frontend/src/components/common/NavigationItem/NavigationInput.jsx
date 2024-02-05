import PropTypes from 'prop-types';
import { useState, useRef, useEffect } from 'react';
import * as S from './NavigationItem.styled';
import Button from '../Button/Button';
import { patchDailry } from '../../../apis/dailryApi';
import { NavigationItemIcon } from '../../../assets/svg';

const NavigationInput = (props) => {
  const { dailryId, setEditingDailry, title } = props;
  const inputRef = useRef(null);
  const [inputTitle, setInputTitle] = useState(title);

  useEffect(() => {
    inputRef.current.focus();
  }, []);

  const cancelEditing = () => {
    setEditingDailry(null);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (inputTitle === '') {
      cancelEditing();
      return;
    }
    patchDailry({ dailryId, title: inputTitle }).then(() => {
      setEditingDailry(null);
    });
  };

  const handleChange = (e) => {
    setInputTitle(e.target.value);
  };

  return (
    <>
      <S.CreateForm onSubmit={handleSubmit}>
        <S.IconWrapper>
          <NavigationItemIcon />
        </S.IconWrapper>
        <S.InputArea
          type="text"
          name={'title'}
          onChange={handleChange}
          value={inputTitle}
          ref={inputRef}
          placeholder={'다일리 제목'}
        />
        <Button type={'submit'} size={'sm'}>
          확인
        </Button>
      </S.CreateForm>
      <S.Overlay onClick={cancelEditing} />
    </>
  );
};

NavigationInput.propTypes = {
  dailryId: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
  setEditingDailry: PropTypes.func.isRequired,
};

export default NavigationInput;
