import { FC, ReactNode } from 'react'
import styled from 'styled-components'
import { styles } from '../layouts/styles/styles'

export const PageContentActions: FC<Props> = (props) => (
    <Container>
        {props.children}
    </Container>
)

const Container = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 20px;
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid ${styles.borderColor};
`

interface Props {
    children?: ReactNode
}
