ACC.reactreviews = {

	_autoload: [
		["bindReactReviews", $(".js-review-reaction").length > 0]
	],

	bindReactReviews: function(){
        $('.js-review-reaction').on({
            click: function react(){
                var reactionType = $(this).data("reaction-type");
                var reviewId = $(this).data("review-id");
                var userReacted = $(this).data("user-reacted");
                var url = userReacted ? $("#reviews").data("remove-react-review") : $("#reviews").data("react-review");
                $.ajax({
                    type: "POST",
                    url: url,
                    data: {"reviewId": reviewId, "reaction": reactionType},
                    dataType: "json",
                    success: function(response){
                        $("#reactions-" + reviewId).html(response.message);
                    }
                });
            }
        });
	}

};