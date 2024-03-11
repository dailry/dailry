import { commonDecorateComponentProperties } from './properties';

const getRelativePosition = (e, pageRef) => {
  const parentX = pageRef?.current?.getBoundingClientRect().left.toFixed();
  const parentY = pageRef?.current?.getBoundingClientRect().top.toFixed();

  const { clientX, clientY } = e;

  return { x: clientX - parentX, y: clientY - parentY };
};

export const getCommonDecorateComponentProperties = (e, pageRef) => {
  return {
    ...commonDecorateComponentProperties,
    id: `decorate-component-${new Date().toISOString()}`,
    position: getRelativePosition(e, pageRef),
    initialStyle: {
      ...commonDecorateComponentProperties.initialStyle,
      position: getRelativePosition(e, pageRef),
    },
  };
};
