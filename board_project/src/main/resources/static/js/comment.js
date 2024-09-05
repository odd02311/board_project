function showEditForm(commentId) {
     document.getElementById('commentContent-' + commentId).style.display = 'none';
     document.getElementById('editCommentForm-' + commentId).style.display = 'block';
 }
