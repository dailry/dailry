import { useEffect, useState } from 'react';
import * as Yup from 'yup';
import {
  getCheckNickName,
  getCheckUserName,
  postJoinMember,
} from '../apis/memberApi';
import useForm from './useForm';

const useJoinForm = (setJoinCompletedMember) => {
  const [isUserNameDuplicated, setIsUserNameDuplicated] = useState(undefined);
  const [isNickNameDuplicated, setIsNickNameDuplicated] = useState(undefined);

  const handleSubmit = async (values) => {
    if (isUserNameDuplicated || values.checkPassword !== values.password)
      return;
    await postJoinMember(values);
    setJoinCompletedMember({ nickname: values.nickname });
  };

  const { form: joinForm, handleChangeForm } = useForm({
    initialValues: {
      username: '',
      password: '',
      nickname: '',
      checkPassword: '',
    },
    handleSubmit,
    validationSchema: Yup.object().shape({
      username: Yup.string()
        .required('아이디를 입력해주세요')
        .min(4, '4자리 이상 입력해주세요')
        .max(15, '15자리 이내로 입력해주세요')
        .matches(/^[a-zA-Z0-9_]+$/i, '영어와 숫자 만 입력해주세요'),
      nickname: Yup.string()
        .required('닉네임을 입력해주세요')
        .min(2, '2자리 이상 입력해주세요')
        .max(30, '30자리 이내로 입력해주세요')
        .matches(
          /^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9_]+$/i,
          '한글과 영어, 숫자 만 입력해주세요',
        ),
      password: Yup.string()
        .required('비밀번호를 입력해주세요')
        .min(8, '8자리 이상 비밀번호를 입력해주세요')
        .max(20, '20자리 이내로 입력해주세요'),
      checkPassword: Yup.string().required('비밀번호를 확인해주세요'),
    }),
  });

  const { values, errors } = joinForm;

  const checkIsUserNameDuplicated = async () => {
    if (!values.username || errors.username) {
      return;
    }
    const result = await getCheckUserName(values.username);
    setIsUserNameDuplicated(result);
  };

  const checkIsNickNameDuplicated = async () => {
    if (!values.nickname || errors.nickname) {
      return;
    }
    const result = await getCheckNickName(values.nickname);
    setIsNickNameDuplicated(result);
  };

  useEffect(() => {
    setIsUserNameDuplicated(undefined);
  }, [values.username]);

  useEffect(() => {
    setIsNickNameDuplicated(undefined);
  }, [values.nickname]);

  return {
    joinForm,
    handleChangeForm,
    isUserNameDuplicated,
    isNickNameDuplicated,
    checkIsUserNameDuplicated,
    checkIsNickNameDuplicated,
  };
};

export default useJoinForm;
