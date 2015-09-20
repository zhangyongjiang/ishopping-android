package com.nextshopper.rest;

import com.nextshopper.rest.beans.ASTracking;
import com.nextshopper.rest.beans.AccountType;
import com.nextshopper.rest.beans.BankInfo;
import com.nextshopper.rest.beans.CancelRequest;
import com.nextshopper.rest.beans.CartItemDetailsList;
import com.nextshopper.rest.beans.CartItemRequest;
import com.nextshopper.rest.beans.CartItemRequestList;
import com.nextshopper.rest.beans.ChangePasswordRequest;
import com.nextshopper.rest.beans.CheckoutRequest;
import com.nextshopper.rest.beans.CouponDetails;
import com.nextshopper.rest.beans.CreditCardBillingInfo;
import com.nextshopper.rest.beans.FacebookToken;
import com.nextshopper.rest.beans.Favorite;
import com.nextshopper.rest.beans.GenericResponse;
import com.nextshopper.rest.beans.LikeRequest;
import com.nextshopper.rest.beans.ListFavoriteDetails;
import com.nextshopper.rest.beans.ListFollowingStore;
import com.nextshopper.rest.beans.ListWrapper;
import com.nextshopper.rest.beans.LoginRequest;
import com.nextshopper.rest.beans.MailMessage;
import com.nextshopper.rest.beans.Memo;
import com.nextshopper.rest.beans.Message;
import com.nextshopper.rest.beans.MessageDetails;
import com.nextshopper.rest.beans.MessageDetailsList;
import com.nextshopper.rest.beans.MessageThread;
import com.nextshopper.rest.beans.MyStore;
import com.nextshopper.rest.beans.NextShopperNumbers;
import com.nextshopper.rest.beans.Notification;
import com.nextshopper.rest.beans.NotificationList;
import com.nextshopper.rest.beans.OrderItemList;
import com.nextshopper.rest.beans.OrderItemNote;
import com.nextshopper.rest.beans.OrderStatus;
import com.nextshopper.rest.beans.PaymentToken;
import com.nextshopper.rest.beans.Product;
import com.nextshopper.rest.beans.ProductCategory;
import com.nextshopper.rest.beans.ProductDetails;
import com.nextshopper.rest.beans.ProductList;
import com.nextshopper.rest.beans.ProductReview;
import com.nextshopper.rest.beans.ProductReviewDetailsList;
import com.nextshopper.rest.beans.Promotion;
import com.nextshopper.rest.beans.PushNotificationToken;
import com.nextshopper.rest.beans.RefundRequest;
import com.nextshopper.rest.beans.RegisterRequest;
import com.nextshopper.rest.beans.Resource;
import com.nextshopper.rest.beans.ReturnRequest;
import com.nextshopper.rest.beans.SearchableProductList;
import com.nextshopper.rest.beans.SearchableUserList;
import com.nextshopper.rest.beans.SellerCommentOnProductReview;
import com.nextshopper.rest.beans.ShippingInfo;
import com.nextshopper.rest.beans.Store;
import com.nextshopper.rest.beans.StoreBasicInfo;
import com.nextshopper.rest.beans.StoreDetails;
import com.nextshopper.rest.beans.StoreDetailsList;
import com.nextshopper.rest.beans.StoreMoneyFlowDetails;
import com.nextshopper.rest.beans.StorePayment;
import com.nextshopper.rest.beans.StoreReview;
import com.nextshopper.rest.beans.StoreSalesSummary;
import com.nextshopper.rest.beans.StoreStatementDetails;
import com.nextshopper.rest.beans.StoreStatus;
import com.nextshopper.rest.beans.StringList;
import com.nextshopper.rest.beans.SupportedVersions;
import com.nextshopper.rest.beans.SystemConfiguration;
import com.nextshopper.rest.beans.Tracking;
import com.nextshopper.rest.beans.TrendProductList;
import com.nextshopper.rest.beans.TwitterToken;
import com.nextshopper.rest.beans.User;
import com.nextshopper.rest.beans.UserActivityList;
import com.nextshopper.rest.beans.UserBasicInfo;
import com.nextshopper.rest.beans.UserDetails;
import com.nextshopper.rest.beans.UserFollowDetailsList;
import com.nextshopper.rest.beans.UserInfo;
import com.nextshopper.rest.beans.UserInterest;
import com.nextshopper.rest.beans.UserOrder;
import com.nextshopper.rest.beans.UserOrderDetails;
import com.nextshopper.rest.beans.UserOrderDetailsList;
import com.nextshopper.rest.beans.UserSettings;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

public interface NextShopperService {
	@POST("/ws/admin/suspend-store/{storeId}")
	void AdminAPI_SuspendStore(@Path("storeId") String storeId, Callback<GenericResponse> callback);

	@POST("/ws/admin/unsuspend-store/{storeId}")
	void AdminAPI_UnsuspendStore(@Path("storeId") String storeId, Callback<GenericResponse> callback);

	@POST("/ws/admin/set-super-user/{userId}")
	void AdminAPI_SetSuperUser(@Path("userId") String userId, Callback<GenericResponse> callback);

	@POST("/ws/admin/suspend-user/{userId}")
	void AdminAPI_SuspendUser(@Path("userId") String userId, Callback<GenericResponse> callback);

	@POST("/ws/admin/unsuspend-user/{userId}")
	void AdminAPI_UnsuspendUser(@Path("userId") String userId, Callback<GenericResponse> callback);

	@POST("/ws/admin/flag/product/{productId}")
	void AdminAPI_FlagProduct(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/admin/suspend/product/{productId}")
	void AdminAPI_SuspendProduct(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/admin/unsuspend/product/{productId}")
	void AdminAPI_UnsuspendProduct(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/admin/flag/user/{against}")
	void AdminAPI_FlagUser(@Path("against") String against, Callback<GenericResponse> callback);

	@POST("/ws/admin/flag/store/{against}")
	void AdminAPI_FlagStore(@Path("against") String against, Callback<GenericResponse> callback);

	@GET("/ws/admin/flag/list")
	void AdminAPI_ListFlags(@Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@POST("/ws/admin/touch-beans/{className}")
	void AdminAPI_TouchBeans(@Path("className") String className, Callback<GenericResponse> callback);

	@POST("/ws/admin/touch-all-beans")
	void AdminAPI_TouchAllBeans(Callback<GenericResponse> callback);

	@GET("/ws/admin/numbers")
	void AdminAPI_GetNumbers(Callback<NextShopperNumbers> callback);

	@POST("/ws/admin/remove-user-account/{userId}/{type}")
	void AdminAPI_RemoveUserAccount(@Path("userId") String userId, @Path("type") AccountType type, Callback<GenericResponse> callback);

	@POST("/ws/admin/check-store-status")
	void AdminAPI_CheckStoreStatus(Callback<GenericResponse> callback);

	@POST("/ws/admin/verify-shipping-number")
	void AdminAPI_VerifyShippingNumber(Callback<GenericResponse> callback);

	@POST("/ws/admin/remove-all-orders")
	void AdminAPI_RemoveAllOrders(Callback<GenericResponse> callback);

	@POST("/ws/admin/remove-all-money-flow")
	void AdminAPI_RemoveAllMoneyFlow(Callback<GenericResponse> callback);

	@GET("/ws/admin/set-system-time-offset")
	void AdminAPI_SetSystemTimeOffset(@Query("day") long day, @Query("hour") long hour, @Query("minute") long minute, @Query("yyyyMMdd") String yyyyMMdd, Callback<GenericResponse> callback);

	@POST("/ws/admin/test-order-shipped/{orderId}")
	void AdminAPI_OrderShipped(@Path("orderId") String orderId, Callback<GenericResponse> callback);

	@POST("/ws/admin/cancel-unshipped-orders")
	void AdminAPI_CancelUnshippedOrders(Callback<GenericResponse> callback);

	@POST("/ws/admin/remove-object/{objId}")
	void AdminAPI_RemoveObject(@Path("objId") String objId, Callback<GenericResponse> callback);

	@POST("/ws/favorite/add")
	void FavoriteAPI_AddFavorite(@Body LikeRequest item, Callback<Favorite> callback);

	@POST("/ws/favorite/remove/{favId}")
	void FavoriteAPI_RemoveFavorite(@Path("favId") String favId, Callback<GenericResponse> callback);

	@GET("/ws/favorite/list")
	void FavoriteAPI_FavoriteList(Callback<ListFavoriteDetails> callback);

	@GET("/ws/message/sys/msg-list")
	void MessageAPI_GetSystemMessages(@Query("offset") int offset, @Query("size") int size, Callback<MessageDetailsList> callback);

	@GET("/ws/message/user/msg-list")
	void MessageAPI_GetUserMessages(@Query("offset") int offset, @Query("size") int size, Callback<MessageDetailsList> callback);

	@GET("/ws/message/store/msg-list")
	void MessageAPI_GetStoreMessages(@Query("offset") int offset, @Query("size") int size, Callback<MessageDetailsList> callback);

	@POST("/ws/message/user/mark-as-read/{msgId}")
	void MessageAPI_MarkUserMsgAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/user/mark-all-read")
	void MessageAPI_MarkAllUserMsgAsRead(Callback<GenericResponse> callback);

	@POST("/ws/message/user/mark-thread-as-read/{msgId}")
	void MessageAPI_MarkUserMsgThreadAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/sys/mark-as-read/{msgId}")
	void MessageAPI_MarksysMsgAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/sys/mark-thread-as-read/{msgId}")
	void MessageAPI_MarksysMsgThreadAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/store/mark-as-read/{msgId}")
	void MessageAPI_MarkMsgAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/store/mark-thread-as-read/{msgId}")
	void MessageAPI_MarkMsgThreadAsRead(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/user/to-store/{storeId}")
	void MessageAPI_UserToStoreMessage(@Path("storeId") String storeId, @Body Message msg, Callback<Message> callback);

	@POST("/ws/message/user/to-system")
	void MessageAPI_UserToSystemMessage(@Body Message msg, Callback<Message> callback);

	@POST("/ws/message/email-us")
	void MessageAPI_EmailUs(@Body MailMessage msg, Callback<GenericResponse> callback);

	@POST("/ws/message/send-invitation-code")
	void MessageAPI_SendInvidationCode(@Body MailMessage msg, Callback<GenericResponse> callback);

	@POST("/ws/message/system/to-user")
	void MessageAPI_SystemToUserMessage(@Body Message msg, Callback<Message> callback);

	@POST("/ws/message/system/to-store")
	void MessageAPI_SystemToStoreMessage(@Body Message msg, Callback<Message> callback);

	@POST("/ws/message/store/to-user/{toUserId}")
	void MessageAPI_StoreToUserMessage(@Path("toUserId") String toUserId, @Body Message msg, Callback<Message> callback);

	@POST("/ws/message/store/email-user-for-order")
	void MessageAPI_StoreToUserEamilMessage(@Body Message msg, Callback<GenericResponse> callback);

	@POST("/ws/message/store/to-system")
	void MessageAPI_StoreToSystemMessage(@Body Message msg, Callback<Message> callback);

	@POST("/ws/message/user/delete/{msgId}")
	void MessageAPI_DeleteUserMessage(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/user/delete")
	void MessageAPI_DeleteUserMessageList(@Body StringList list, Callback<GenericResponse> callback);

	@POST("/ws/message/sys/delete/{msgId}")
	void MessageAPI_DeleteSysMessage(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/sys/delete")
	void MessageAPI_DeleteSysMessageList(@Body StringList list, Callback<GenericResponse> callback);

	@POST("/ws/message/store/delete/{msgId}")
	void MessageAPI_DeleteStoreMessage(@Path("msgId") String msgId, Callback<GenericResponse> callback);

	@POST("/ws/message/store/delete")
	void MessageAPI_DeleteStoreMessageList(@Body StringList list, Callback<GenericResponse> callback);

	@POST("/ws/message/user/delete-all")
	void MessageAPI_DeleteAllUserMessages(Callback<GenericResponse> callback);

	@POST("/ws/message/store/delete-all")
	void MessageAPI_DeleteAllStoreMessages(Callback<GenericResponse> callback);

	@GET("/ws/message/user/{msgId}")
	void MessageAPI_GetUserMessageById(@Path("msgId") String msgId, Callback<MessageDetails> callback);

	@GET("/ws/message/sys/{msgId}")
	void MessageAPI_GetSysMessageById(@Path("msgId") String msgId, Callback<MessageDetails> callback);

	@GET("/ws/message/store/{msgId}")
	void MessageAPI_GetStoreMessageById(@Path("msgId") String msgId, Callback<MessageDetails> callback);

	@GET("/ws/message/user/{msgId}/threads")
	void MessageAPI_GetUserMessageThreadsByMsgId(@Path("msgId") String msgId, Callback<MessageThread> callback);

	@GET("/ws/message/store/{msgId}/threads")
	void MessageAPI_GetStoreMessageThreadsByMsgId(@Path("msgId") String msgId, Callback<MessageThread> callback);

	@GET("/ws/message/sys/{msgId}/threads")
	void MessageAPI_GetSysMessageThreadsByMsgId(@Path("msgId") String msgId, Callback<MessageThread> callback);

	@POST("/ws/message/user/reply/{msgId}")
	void MessageAPI_UserReplyMessage(@Path("msgId") String msgId, @Body Message msg, Callback<Message> callback);

	@POST("/ws/message/sys/reply/{msgId}")
	void MessageAPI_SysReplyMessage(@Path("msgId") String msgId, @Body Message msg, Callback<Message> callback);

	@POST("/ws/message/store/reply/{msgId}")
	void MessageAPI_StoreReplyMessage(@Path("msgId") String msgId, @Body Message msg, Callback<Message> callback);

	@POST("/ws/message/reindex-all")
	void MessageAPI_ReindexAll(Callback<GenericResponse> callback);

	@GET("/ws/misc/tags")
	void MiscAPI_ListCategories(Callback<ListWrapper> callback);

	@GET("/ws/misc/categories")
	void MiscAPI_ListAllCategories(Callback<ProductCategory> callback);

	@GET("/ws/misc/supported-versions")
	void MiscAPI_GetSupportedVersion(Callback<SupportedVersions> callback);

	@GET("/ws/misc/sys-conf")
	void MiscAPI_GetSysConf(Callback<SystemConfiguration> callback);

	@GET("/ws/order/search")
	void OrderAPI_Search(@Query("keywords") String keywords, @Query("storeId") String storeId, @Query("status") OrderStatus status, @Query("offset") int offset, @Query("size") int size, Callback<UserOrderDetailsList> callback);

	@POST("/ws/order/{orderId}/set-tracking")
	void OrderAPI_SetTracking(@Path("orderId") String orderId, @Body Tracking tracking, Callback<UserOrder> callback);

	@POST("/ws/order/reindex-all")
	void OrderAPI_ReindexAll(Callback<GenericResponse> callback);

	@GET("/ws/order/by-id")
	void OrderAPI_GetOrderById(@Query("orderId") String orderId, Callback<UserOrderDetails> callback);

	@GET("/ws/order/by-number")
	void OrderAPI_GetOrderByNumber(@Query("orderNumber") String orderNumber, Callback<UserOrderDetails> callback);

	@POST("/ws/order/cancel-by-user")
	void OrderAPI_CancelOrderByUser(@Body CancelRequest req, Callback<GenericResponse> callback);

	@POST("/ws/order/cancel-by-admin")
	void OrderAPI_CancelOrderByAdmin(@Body CancelRequest req, Callback<GenericResponse> callback);

	@POST("/ws/order/user-return-request")
	void OrderAPI_ReturnRequestedByUser(@Body ReturnRequest req, Callback<GenericResponse> callback);

	@POST("/ws/order/cancel-by-seller")
	void OrderAPI_CancelOrderBySeller(@Body CancelRequest req, Callback<GenericResponse> callback);

	@POST("/ws/order/add-seller-memo/{orderId}")
	void OrderAPI_AddSellerMemo(@Path("orderId") String orderId, @Body Memo memo, Callback<GenericResponse> callback);

	@POST("/ws/order/refund")
	void OrderAPI_Refund(@Body RefundRequest req, Callback<GenericResponse> callback);

	@GET("/ws/order/user-order-list")
	void OrderAPI_UserOrderList(@Query("offset") int offset, @Query("size") int size, Callback<UserOrderDetailsList> callback);

	@GET("/ws/order/user-order-item-list")
	void OrderAPI_UserOrderItemList(@Query("offset") int offset, @Query("size") int size, Callback<OrderItemList> callback);

	@GET("/ws/order/store-order-list")
	void OrderAPI_StoreOrderList(@Query("offset") int offset, @Query("size") int size, @Query("status") OrderStatus status, Callback<UserOrderDetailsList> callback);

	@GET("/ws/order/{orderId}/shipping-status")
	void OrderAPI_GetShippingStatus(@Path("orderId") String orderId, Callback<ASTracking> callback);

	@GET("/ws/order/tracking/carriers")
	void OrderAPI_ListCourier(Callback<ListWrapper> callback);

	@POST("/ws/order/set-order-item-note/{orderItemId}")
	void OrderAPI_SetDeliverQuality(@Path("orderItemId") String orderItemId, @Body OrderItemNote note, Callback<GenericResponse> callback);

	@GET("/ws/order/messages/{orderId}")
	void OrderAPI_ListOrderMessages(@Path("orderId") String orderId, Callback<MessageDetailsList> callback);

	@GET("/ws/payment/store-payment-history")
	void PaymentAPI_History(@Query("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/payment/store-cash-flow")
	void PaymentAPI_StoreCashFlowBalance(@Query("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/payment/store-statement")
	void PaymentAPI_StoreStatement(@Query("storeId") String storeId, @Query("year") int year, @Query("month") int month, Callback<StoreStatementDetails> callback);

	@GET("/ws/payment/store-sales-summary")
	void PaymentAPI_GetStoreSalesSummary(@Query("storeId") String storeId, @Query("startInclude") String startInclude, @Query("endExclude") String endExclude, Callback<StoreSalesSummary> callback);

	@GET("/ws/payment/by-id/{moneyFlowId}")
	void PaymentAPI_GetStoreMoneyFlowDetails(@Path("moneyFlowId") String moneyFlowId, Callback<StoreMoneyFlowDetails> callback);

	@POST("/ws/payment/pay-store")
	void PaymentAPI_PayStore(@Body StorePayment storePayment, Callback<GenericResponse> callback);

	@POST("/ws/payment/pay-all-stores")
	void PaymentAPI_PayAllStores(Callback<GenericResponse> callback);

	@GET("/ws/product/{productId}")
	void ProductAPI_Get(@Path("productId") String productId, Callback<ProductDetails> callback);

	@GET("/ws/product/my-product-list")
	void ProductAPI_List(@Query("offset") int offset, @Query("size") int size, Callback<ProductList> callback);

	@GET("/ws/product/search")
	void ProductAPI_Search(@Query("keywords") String keywords, @Query("category") String category, @Query("storeId") String storeId, @Query("minPrice") Float minPrice, @Query("maxPrice") Float maxPrice, @Query("offset") int offset, @Query("size") int size, @Query("activeOnly") Boolean activeOnly, Callback<ProductList> callback);

	@POST("/ws/product/create")
	void ProductAPI_Create(@Body Product prod, Callback<Product> callback);

	@POST("/ws/product/remove/{id}")
	void ProductAPI_RemoveProduct(@Path("id") String id, Callback<GenericResponse> callback);

	@POST("/ws/product/remove")
	void ProductAPI_RemoveProducts(@Body StringList prodIds, Callback<GenericResponse> callback);

	@POST("/ws/product/update")
	void ProductAPI_UpdateProduct(@Body Product product, Callback<Product> callback);

	@POST("/ws/product/update-quantity/{productId}/{quantity}")
	void ProductAPI_UpdateProductQuantity(@Path("productId") String productId, @Path("quantity") int quantity, Callback<Product> callback);

	@POST("/ws/product/update-price/{productId}/{price}")
	void ProductAPI_UpdateProductPrice(@Path("productId") String productId, @Path("price") float price, Callback<Product> callback);

	@POST("/ws/product/deactive/{productId}")
	void ProductAPI_Deactive(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/product/deactive")
	void ProductAPI_DeactiveProducts(@Body StringList prodIds, Callback<GenericResponse> callback);

	@POST("/ws/product/active")
	void ProductAPI_ActiveProducts(@Body StringList prodIds, Callback<GenericResponse> callback);

	@POST("/ws/product/active-all")
	void ProductAPI_ActiveAll(Callback<GenericResponse> callback);

	@POST("/ws/product/remove-options")
	void ProductAPI_RemoveOptions(Callback<GenericResponse> callback);

	@POST("/ws/product/fix-descriptions")
	void ProductAPI_FixDescriptions(Callback<GenericResponse> callback);

	@GET("/ws/product/store-public-product-list")
	void ProductAPI_ListStorePublicProducts(@Query("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ProductList> callback);

	@Multipart
	@POST("/ws/product/add-resource/{productId}")
	void ProductAPI_Upload(@Path("productId") String productId, @Part("file") TypedFile inputStream, @Part("isImage") String isImage, Callback<Product> callback);

	@Multipart
	@POST("/ws/product/add-attachment/{productId}")
	void ProductAPI_UploadAttachment(@Path("productId") String productId, @Part("file") TypedFile inputStream, @Part("title") String title, @Part("description") String description, @Part("isImage") String isImage, Callback<Product> callback);

	@POST("/ws/product/add-attachment/{productId}")
	void ProductAPI_AddAttachment(@Path("productId") String productId, @Query("title") String title, @Query("url") String url, Callback<Product> callback);

	@POST("/ws/product/remove-image/{productId}/{imageIndex}")
	void ProductAPI_RemoveResource(@Path("productId") String productId, @Path("imageIndex") int imageIndex, Callback<Product> callback);

	@POST("/ws/product/remove-attachment/{productId}/{attachmentIndex}")
	void ProductAPI_RemoveAttachment(@Path("productId") String productId, @Path("attachmentIndex") int attachmentIndex, Callback<Product> callback);

	@GET("/ws/product/latest")
	void ProductAPI_NewProducts(@Query("offset") int offset, @Query("size") int size, Callback<SearchableProductList> callback);

	@GET("/ws/product/latest-products")
	void ProductAPI_LatestProducts(@Query("offset") int offset, @Query("size") int size, Callback<ProductList> callback);

	@GET("/ws/product/recommendation-for-user")
	void ProductAPI_RecommendationsForUser(@Query("offset") int offset, @Query("size") int size, @Query("cat") String cat, @Query("prodId") String prodId, Callback<SearchableProductList> callback);

	@GET("/ws/product/recommendation-for-product")
	void ProductAPI_RecommendationsForProduct(@Query("offset") int offset, @Query("size") int size, @Query("productId") String productId, Callback<SearchableProductList> callback);

	@GET("/ws/product/recommendation-for-category")
	void ProductAPI_RecommendationsForCategory(@Query("offset") int offset, @Query("size") int size, @Query("category") String category, Callback<SearchableProductList> callback);

	@GET("/ws/product/popular")
	void ProductAPI_PopularProducts(@Query("keywords") String keywords, @Query("offset") int offset, @Query("size") int size, Callback<TrendProductList> callback);

	@POST("/ws/product/reindex-all-products")
	void ProductAPI_ReindexAllProducts(Callback<GenericResponse> callback);

	@POST("/ws/product/reindex-all-stores")
	void ProductAPI_ReindexAllStores(Callback<GenericResponse> callback);

	@POST("/ws/product/set-ranking/{productId}/{ranking}")
	void ProductAPI_SetProductRanking(@Path("productId") String productId, @Path("ranking") int ranking, Callback<GenericResponse> callback);

	@Multipart
	@POST("/ws/product/create-from-zip")
	void ProductAPI_CreateFromExcelZip(@Part("email") String email, @Part("password") String password, @Part("file") TypedFile inputStream, Callback<String> callback);

	@POST("/ws/product/active/{productId}")
	void ProductAPI_Active(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/promotion/remove/{id}")
	void PromotionAPI_Remove(@Path("id") String id, Callback<GenericResponse> callback);

	@POST("/ws/promotion/create")
	void PromotionAPI_Create(@Body Promotion prom, Callback<Promotion> callback);

	@POST("/ws/promotion/remove")
	void PromotionAPI_RemoveProducts(@Body StringList promIds, Callback<GenericResponse> callback);

	@POST("/ws/promotion/update")
	void PromotionAPI_UpdateProduct(@Body Promotion prom, Callback<Promotion> callback);

	@POST("/ws/push-notification/remove/{notificationId}")
	void PushNotificationAPI_Remove(@Path("notificationId") String notificationId, Callback<GenericResponse> callback);

	@POST("/ws/push-notification/settings/{token}")
	void PushNotificationAPI_Update(@Path("token") String token, @Body UserSettings settings, Callback<UserSettings> callback);

	@POST("/ws/push-notification/register/apple-device-token")
	void PushNotificationAPI_RegisterAppleDeviceToken(@Body PushNotificationToken token, Callback<GenericResponse> callback);

	@POST("/ws/push-notification/register/google-device-token")
	void PushNotificationAPI_RegisterGoogleDeviceToken(@Body PushNotificationToken token, Callback<GenericResponse> callback);

	@POST("/ws/push-notification/update-settings/")
	void PushNotificationAPI_UpdatePushNotificationSetting(@Body UserSettings settings, Callback<UserSettings> callback);

	@GET("/ws/push-notification/settings/")
	void PushNotificationAPI_GetPushNotificationSetting(Callback<UserSettings> callback);

	@POST("/ws/push-notification/send")
	void PushNotificationAPI_SendNotification(@Body Notification notification, Callback<GenericResponse> callback);

	@GET("/ws/push-notification/mine")
	void PushNotificationAPI_ListUserNotifications(@Query("offset") int offset, @Query("size") int size, Callback<NotificationList> callback);

	@GET("/ws/push-notification/{notificationId}")
	void PushNotificationAPI_GetNotificationById(@Path("notificationId") String notificationId, Callback<Notification> callback);

	@POST("/ws/push-notification/mark-as-read/{notificationId}")
	void PushNotificationAPI_MarkAsRead(@Path("notificationId") String notificationId, Callback<GenericResponse> callback);

	@Multipart
	@POST("/ws/resource/upload-for-user")
	void ResourceAPI_UploadForUser(@Part("file") TypedFile inputStream, @Part("isPrivate") String isPrivate, @Part("isImage") String isImage, Callback<Resource> callback);

	@Multipart
	@POST("/ws/resource/upload-for-store")
	void ResourceAPI_UploadForStore(@Part("file") TypedFile inputStream, @Part("isPrivate") String isPrivate, @Part("isImage") String isImage, Callback<Resource> callback);

	@GET("/ws/resource/list-user-resources")
	void ResourceAPI_ListUserResources(Callback<ListWrapper> callback);

	@GET("/ws/resource/list-store-resources")
	void ResourceAPI_ListStoreResources(Callback<ListWrapper> callback);

	@POST("/ws/resource/delete/{resourceId}")
	void ResourceAPI_DeleteRedource(@Path("resourceId") String resourceId, Callback<GenericResponse> callback);

    @GET("/ws/shopping-cart/list")
    void ShoppingAPI_List(@Query("couponCode")String couponCode, Callback<CartItemDetailsList> callback);

    @POST("/ws/shopping-cart/check-coupon/{couponCode}")
    void ShoppingAPI_CheckCoupon(@Path("couponCode")String couponCode, Callback<CouponDetails> callback);

    @POST("/ws/shopping-cart/add")
    void ShoppingAPI_AddCartItem(@Body CartItemRequest item, Callback<CartItemDetailsList> callback);
    
    @POST("/ws/shopping-cart/add-all")
    void ShoppingAPI_AddCartItems(@Body CartItemRequestList items, Callback<CartItemDetailsList> callback);
    
	@POST("/ws/shopping-cart/remove/{cartItemId}")
	void ShoppingAPI_RemoveCartItem(@Path("cartItemId") String cartItemId, Callback<CartItemDetailsList> callback);

	@POST("/ws/shopping-cart/update-quantity/{cartItemId}/{quantity}")
	void ShoppingAPI_UpdateItemQuantity(@Path("cartItemId") String cartItemId, @Path("quantity") int quantity, Callback<CartItemDetailsList> callback);

	@POST("/ws/shopping-cart/checkout")
	void ShoppingAPI_Checkout(@Body CheckoutRequest req, Callback<UserOrderDetailsList> callback);

	@GET("/ws/shopping-cart/payment-token")
	void ShoppingAPI_GetPaymentToken(@Query("clientId") String clientId, Callback<PaymentToken> callback);

	@GET("/ws/social/user-store-reviews")
	void SocialAPI_ListUserStoreReviews(@Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/social/user-product-reviews")
	void SocialAPI_ListUserProductReviews(@Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@POST("/ws/social/follow-user/{followingUserId}")
	void SocialAPI_FollowUser(@Path("followingUserId") String followingUserId, Callback<GenericResponse> callback);

	@POST("/ws/social/unfollow-user/{followingUserId}")
	void SocialAPI_UnfollowUser(@Path("followingUserId") String followingUserId, Callback<GenericResponse> callback);

	@GET("/ws/social/followers/{userId}")
	void SocialAPI_UserFollowers(@Path("userId") String userId, @Query("offset") int offset, @Query("size") int size, Callback<UserFollowDetailsList> callback);

	@GET("/ws/social/followings/{userId}")
	void SocialAPI_UserFollowings(@Path("userId") String userId, @Query("offset") int offset, @Query("size") int size, Callback<UserFollowDetailsList> callback);

	@POST("/ws/social/follow-store/{storeId}")
	void SocialAPI_FollowStore(@Path("storeId") String storeId, Callback<GenericResponse> callback);

	@POST("/ws/social/unfollow-store/{storeId}")
	void SocialAPI_UnfollowStore(@Path("storeId") String storeId, Callback<GenericResponse> callback);

	@POST("/ws/social/like-product/{productId}")
	void SocialAPI_LikeProduct(@Path("productId") String productId, Callback<GenericResponse> callback);

	@POST("/ws/social/unlike-product/{productId}")
	void SocialAPI_UnlikeProduct(@Path("productId") String productId, Callback<GenericResponse> callback);

	@GET("/ws/social/following-stores/{userId}")
	void SocialAPI_FollowingStores(@Path("userId") String userId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/social/following-stores")
	void SocialAPI_MyFollowingStores(@Query("offset") int offset, @Query("size") int size, Callback<ListFollowingStore> callback);

	@GET("/ws/social/store-followers/{storeId}")
	void SocialAPI_ListStoreFollowers(@Path("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/social/liked-products/{userId}")
	void SocialAPI_LikedProducts(@Path("userId") String userId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@GET("/ws/social/liked-products")
	void SocialAPI_ListMyFavProducts(@Query("offset") int offset, @Query("size") int size, Callback<ListFavoriteDetails> callback);

	@GET("/ws/social/product-likers/{productId}")
	void SocialAPI_ListProductLikers(@Path("productId") String productId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@POST("/ws/social/create-product-review")
	void SocialAPI_ReviewProduct(@Body ProductReview review, Callback<ProductReview> callback);

	@Multipart
	@POST("/ws/social/create-product-review")
	void SocialAPI_ReviewProductWithImage(@Path("json") String json, @Part("file") TypedFile inputStream, @Part("file1") TypedFile inputStream1, @Part("file2") TypedFile inputStream2, @Part("file3") TypedFile inputStream3, @Part("file4") TypedFile inputStream4, Callback<ProductReview> callback);

	@POST("/ws/social/reply-product-review/{reviewId}")
	void SocialAPI_StoreReplyProductReview(@Path("reviewId") String reviewId, @Body SellerCommentOnProductReview reply, Callback<SellerCommentOnProductReview> callback);

	@POST("/ws/social/create-store-review")
	void SocialAPI_ReviewStore(@Body StoreReview review, Callback<StoreReview> callback);

	@POST("/ws/social/remove-product-review/{reviewId}")
	void SocialAPI_RemoveProductReview(@Path("reviewId") String reviewId, Callback<GenericResponse> callback);

	@POST("/ws/social/remove-store-review/{reviewId}")
	void SocialAPI_RemoveStoreReview(@Path("reviewId") String reviewId, Callback<GenericResponse> callback);

	@GET("/ws/social/product-reviews/{productId}")
	void SocialAPI_GetProductReviews(@Path("productId") String productId, @Query("offset") int offset, @Query("size") int size, Callback<ProductReviewDetailsList> callback);

	@GET("/ws/social/store-reviewed-products")
	void SocialAPI_GetStoreReviewedProducts(@Query("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ProductList> callback);

	@GET("/ws/social/store-product-reviews")
	void SocialAPI_GetStoreProductReviews(@Query("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ProductReviewDetailsList> callback);

	@GET("/ws/social/store-reviews/{storeId}")
	void SocialAPI_GetStoreReviews(@Path("storeId") String storeId, @Query("offset") int offset, @Query("size") int size, Callback<ListWrapper> callback);

	@POST("/ws/social/facebook-login")
	void SocialAPI_FacebookLogin(@Body FacebookToken token, Callback<User> callback);

	@POST("/ws/social/twitter-login")
	void SocialAPI_TwitterLogin(@Body TwitterToken token, Callback<User> callback);

	@POST("/ws/store/open")
	void StoreAPI_Register(@Body StoreBasicInfo info, Callback<Store> callback);

	@GET("/ws/store/search")
	void StoreAPI_Search(@Query("status") StoreStatus status, @Query("keywords") String keywords, @Query("offset") int offset, @Query("size") int size, Callback<StoreDetailsList> callback);

	@POST("/ws/store/update")
	void StoreAPI_Update(@Body StoreBasicInfo info, Callback<Store> callback);

	@POST("/ws/store/reindex-all")
	void StoreAPI_ReindexAll(Callback<GenericResponse> callback);

	@GET("/ws/store/details")
	void StoreAPI_GetStoreDetails(@Query("storeId") String storeId, Callback<StoreDetails> callback);

	@Multipart
	@POST("/ws/store/add-store-image")
	void StoreAPI_Upload(@Part("file") TypedFile inputStream, @Part("isImage") String isImage, Callback<Store> callback);

	@POST("/ws/store/remove-store-image/{resourceIndex}")
	void StoreAPI_RemoveResource(@Path("resourceIndex") int resourceIndex, Callback<Store> callback);

	@POST("/ws/store/update-store-profile")
	void StoreAPI_UpdateStoreProfile(@Body StoreBasicInfo info, Callback<Store> callback);

	@POST("/ws/store/update-store-address")
	void StoreAPI_UpdateStoreAddress(@Body StoreBasicInfo info, Callback<Store> callback);

	@GET("/ws/store/mine")
	void StoreAPI_GetMyStore(Callback<MyStore> callback);

	@GET("/ws/store/store-basic-info")
	void StoreAPI_GetStorePublicInfo(@Query("storeId") String storeId, Callback<StoreBasicInfo> callback);

	@POST("/ws/store/update-bank-info")
	void StoreAPI_UpdateBankInfo(@Body BankInfo bankInfo, Callback<GenericResponse> callback);

	@Multipart
	@POST("/ws/store/add-store-owner-identity-image")
	void StoreAPI_UploadOwnerIdentityImage(@Part("file") TypedFile inputStream, Callback<Store> callback);

	@Multipart
	@POST("/ws/store/add-store-company-identity-image")
	void StoreAPI_UploadCompanyIdentityImage(@Part("file") TypedFile inputStream, Callback<Store> callback);

	@Multipart
	@POST("/ws/store/set-store-logo")
	void StoreAPI_UploadLogo(@Part("file") TypedFile inputStream, Callback<Store> callback);

	@POST("/ws/store/verify/{code}")
	void StoreAPI_VerifyStore(@Path("code") String code, Callback<GenericResponse> callback);

	@POST("/ws/store/send-validation-code")
	void StoreAPI_SendValidationCode(Callback<GenericResponse> callback);

	@POST("/ws/store/send-validation-code-to-phone")
	void StoreAPI_SendValidationCodeToPhone(@Body StoreBasicInfo info, Callback<GenericResponse> callback);

	@POST("/ws/user/register")
	void UserAPI_Register(@Body RegisterRequest req, Callback<User> callback);

	@GET("/ws/user/search")
	void UserAPI_Search(@Query("keywords") String keywords, @Query("offset") int offset, @Query("size") int size, Callback<SearchableUserList> callback);

	@POST("/ws/user/update-profile")
	void UserAPI_Update(@Body UserBasicInfo info, Callback<User> callback);

	@POST("/ws/user/reindex-all")
	void UserAPI_ReindexAll(Callback<GenericResponse> callback);

	@Multipart
	@POST("/ws/user/upload-image")
	void UserAPI_Upload(@Part("file") TypedFile inputStream, Callback<User> callback);

	@GET("/ws/user/me")
	void UserAPI_Me(Callback<UserDetails> callback);

	@POST("/ws/user/change-password")
	void UserAPI_ChangePassword(@Body ChangePasswordRequest req, Callback<GenericResponse> callback);

	@POST("/ws/user/reset-password")
	void UserAPI_ResetPassword(@Body LoginRequest req, Callback<GenericResponse> callback);

	@POST("/ws/user/add-shipping")
	void UserAPI_AddShipping(@Body ShippingInfo info, Callback<User> callback);

	@POST("/ws/user/remove-shipping/{shippingId}")
	void UserAPI_RemoveShipping(@Path("shippingId") String shippingId, Callback<GenericResponse> callback);

	@POST("/ws/user/add-billing")
	void UserAPI_AddBilling(@Body CreditCardBillingInfo info, Callback<CreditCardBillingInfo> callback);

	@POST("/ws/user/remove-billing/{billingId}")
	void UserAPI_RemoveBilling(@Path("billingId") String billingId, Callback<GenericResponse> callback);

	@GET("/ws/user/logout")
	void UserAPI_LogoutWithGet(Callback<GenericResponse> callback);

	@GET("/ws/user/profile/{userId}")
	void UserAPI_GetUserDetails(@Path("userId") String userId, Callback<UserInfo> callback);

	@GET("/ws/user/accounts")
	void UserAPI_MyAccounts(Callback<ListWrapper> callback);

	@POST("/ws/user/set-as-agent/{agentId}")
	void UserAPI_SetAsAgent(@Path("agentId") String agentId, Callback<GenericResponse> callback);

	@GET("/ws/user/details/{userId}")
	void UserAPI_GetUserDetailsForSuperUser(@Path("userId") String userId, Callback<UserDetails> callback);

	@GET("/ws/user/details-by-email")
	void UserAPI_GetUserDetailsByEmailForSuperUser(@Query("email") String email, Callback<UserDetails> callback);

	@GET("/ws/user/profile-by-email")
	void UserAPI_GetUserDetailsByEmail(@Query("email") String email, Callback<UserInfo> callback);

	@GET("/ws/user/user-activities")
	void UserAPI_GetUserActivities(@Query("offset") int offset, @Query("size") int size, Callback<UserActivityList> callback);

	@POST("/ws/user/login")
	void UserAPI_Login(@Body LoginRequest req, Callback<User> callback);

	@GET("/ws/user/login")
	void UserAPI_Login(@Query("email") String email, @Query("password") String password, Callback<User> callback);

	@POST("/ws/user/logout")
	void UserAPI_Logout(Callback<GenericResponse> callback);

	@GET("/ws/user/user-interest")
	void UserAPI_GetUserInterest(Callback<UserInterest> callback);

}
