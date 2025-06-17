import styled from "styled-components";
import type { CommentResponse } from "../../api-client";
import Heading from "../../components/Heading";
import CommentListItem from "./CommentListItem";

const StyledDiv = styled.div`
  display: grid;
  gap: 1.2rem;
`;

const StyledUl = styled.ul`
  display: grid;
  gap: 1.2rem;
`;

function CommentList({ commentList }: { commentList: CommentResponse[] }) {
  return (
    <StyledDiv>
      <Heading as="h3">Comments ({commentList.length}):</Heading>
      <StyledUl>
        {commentList.map((c) => (
          <CommentListItem key={c.id} commentItem={c} />
        ))}
      </StyledUl>
    </StyledDiv>
  );
}

export default CommentList;
