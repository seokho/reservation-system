package kr.or.connect.reservation.dao.sql;

/**
 * Created by ODOL on 2017. 7. 12..
 */
public class ProductSqls {
    public final static String SELECT_PRODUCT_DTO_ALL_USE_LIMIT =
            "SELECT P.id, P.category_id, P.name, P.description, DI.place_name, DI.place_street, DI.observation_time," +
                    " DI.display_start, DI.display_end, MAX(F.file_name) file_name, MAX(F.save_file_name) save_file_name"
                    + " FROM product AS P"
                    + " JOIN display_info AS DI"
                    + " ON DI.id = P.id"
                    + " JOIN product_image AS PI"
                    + " ON P.id = PI.product_id"
                    + " JOIN file AS F"
                    + " ON F.id = PI.file_id"
                    + " GROUP BY P.id"
                    + " LIMIT :offset, :limit";

    public final static String SELECT_PRODUCT_DTO_ALL =
            "SELECT P.id, P.category_id, P.name, P.description, DI.place_name, DI.place_street, DI.observation_time," +
                    " DI.display_start, DI.display_end, MAX(F.file_name) file_name, MAX(F.save_file_name) save_file_name"
                    + " FROM product AS P"
                    + " JOIN display_info AS DI"
                    + " ON DI.id = P.id"
                    + " JOIN product_image AS PI"
                    + " ON P.id = PI.product_id"
                    + " JOIN file AS F"
                    + " ON F.id = PI.file_id"
                    + " GROUP BY P.id";

    public final static String SELECT_PRODUCT_DTO_LIST_BY_CATEGORY =
            "SELECT P.id, P.category_id, P.name, P.description, DI.place_name, DI.place_street, DI.observation_time," +
                    " DI.display_start, DI.display_end, MAX(F.file_name) file_name, MAX(F.save_file_name) save_file_name"
                    + " FROM product AS P"
                    + " JOIN display_info AS DI"
                    + " ON DI.id = P.id"
                    + " JOIN product_image AS PI"
                    + " ON P.id = PI.product_id"
                    + " JOIN file AS F"
                    + " ON F.id = PI.file_id"
                    + " WHERE P.category_id = :category"
                    + " GROUP BY P.id"
                    + " LIMIT :offset, :limit";

    public final static String SELECT_PRODUCT_DTO_BY_PRODUCT_ID =
            "SELECT P.id, P.category_id, P.name, P.description, DI.place_name, DI.place_street, DI.observation_time," +
                    " DI.display_start, DI.display_end, MAX(F.file_name) file_name, MAX(F.save_file_name) save_file_name"
                    + " FROM product AS P"
                    + " JOIN display_info AS DI"
                    + " ON DI.id = P.id"
                    + " JOIN product_image AS PI"
                    + " ON P.id = PI.product_id"
                    + " JOIN file AS F"
                    + " ON F.id = PI.file_id"
                    + " GROUP BY P.id"
                    + " HAVING P.id = :id";

    public final static String SELECT_PRODUCT_PRICE_LIST_BY_PRODUCT_ID =
            "SELECT P.id, PP.price_type, PP.price, PP.discount_rate" +
                    " FROM product AS P" +
                    " JOIN product_price AS PP" +
                    " ON P.id = PP.product_id" +
                    " WHERE P.id = :id";
}
