/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 部署リポジトリインターフェース
 * 
 * <p>部署エンティティに対するデータアクセス操作を定義します。
 * Spring Data JPAを使用してCRUD操作およびカスタムクエリを提供します。
 * Hexagonal Architectureのポート（インターフェース）として機能します。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * 部署名で部署を検索します
     * 
     * @param departmentName 検索する部署名
     * @return 該当する部署のOptional
     */
    Optional<Department> findByName(String name);

    /**
     * 部署名で部署を部分一致検索します
     * 
     * @param departmentName 検索する部署名（部分一致）
     * @return 該当する部署のリスト
     */
    List<Department> findByNameContaining(String name);

    /**
     * 親部署IDで子部署を検索します
     * 
     * @param parentDepartmentId 親部署ID
     * @return 該当する子部署のリスト
     */
    List<Department> findByParentId(Long parentId);

    /**
     * 親部署IDで子部署を表示順序でソートして検索します
     * 
     * @param parentDepartmentId 親部署ID
     * @return 表示順序でソートされた子部署のリスト
     */
    List<Department> findByParentIdOrderByDisplayOrder(Long parentId);

    /**
     * マネージャーIDで部署を検索します
     * 
     * @param managerId マネージャーID
     * @return 該当する部署のリスト
     */
    List<Department> findByManagerId(Long managerId);

    /**
     * アクティブな部署を検索します
     * 
     * @param isActive アクティブフラグ
     * @return アクティブな部署のリスト
     */
    List<Department> findByActive(Boolean active);

    /**
     * アクティブな部署を表示順序でソートして検索します
     * 
     * @param isActive アクティブフラグ
     * @return 表示順序でソートされたアクティブな部署のリスト
     */
    List<Department> findByActiveOrderByDisplayOrder(Boolean active);

    /**
     * 全ての部署を表示順序でソートして取得します
     * 
     * @return 表示順序でソートされた全部署のリスト
     */
    List<Department> findAllByOrderByDisplayOrder();

    /**
     * 指定された部署の階層構造を取得します（再帰クエリ）
     * 
     * @param departmentId 起点となる部署ID
     * @return 階層構造の部署リスト
     */
    @Query(value = """
        WITH RECURSIVE department_hierarchy AS (
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, 0 as level
            FROM departments d
            WHERE d.id = :departmentId
            UNION ALL
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, dh.level + 1
            FROM departments d
            INNER JOIN department_hierarchy dh ON d.parent_id = dh.id
        )
        SELECT * FROM department_hierarchy ORDER BY level, display_order
        """, nativeQuery = true)
    List<Department> findDepartmentHierarchy(@Param("departmentId") Long departmentId);

    /**
     * 指定された部署の上位階層を取得します（逆再帰クエリ）
     * 
     * @param departmentId 起点となる部署ID
     * @return 上位階層の部署リスト
     */
    @Query(value = """
        WITH RECURSIVE parent_hierarchy AS (
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, 0 as level
            FROM departments d
            WHERE d.id = :departmentId
            UNION ALL
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, ph.level + 1
            FROM departments d
            INNER JOIN parent_hierarchy ph ON d.id = ph.parent_id
        )
        SELECT * FROM parent_hierarchy ORDER BY level DESC
        """, nativeQuery = true)
    List<Department> findParentHierarchy(@Param("departmentId") Long departmentId);

    /**
     * ルート部署（親部署がない部署）を取得します
     * 
     * @return ルート部署のリスト
     */
    List<Department> findByParentIdIsNull();

    /**
     * ルート部署を表示順序でソートして取得します
     * 
     * @return 表示順序でソートされたルート部署のリスト
     */
    List<Department> findByParentIdIsNullOrderByDisplayOrder();

    /**
     * 指定された階層レベルの部署を取得します
     * 
     * @param level 階層レベル（0がルート）
     * @return 指定階層レベルの部署リスト
     */
    @Query(value = """
        WITH RECURSIVE department_levels AS (
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, 0 as level
            FROM departments d
            WHERE d.parent_id IS NULL
            UNION ALL
            SELECT d.id, d.name, d.parent_id, d.manager_id,
                   d.display_order, d.active, d.created_at, d.updated_at, dl.level + 1
            FROM departments d
            INNER JOIN department_levels dl ON d.parent_id = dl.id
        )
        SELECT * FROM department_levels WHERE level = :level ORDER BY display_order
        """, nativeQuery = true)
    List<Department> findByLevel(@Param("level") Integer level);

    /**
     * 部署名とアクティブフラグで検索します
     * 
     * @param departmentName 部署名（部分一致）
     * @param isActive アクティブフラグ
     * @return 該当する部署のリスト
     */
    List<Department> findByNameContainingAndActive(String name, Boolean active);

    /**
     * 親部署IDとアクティブフラグで検索します
     * 
     * @param parentDepartmentId 親部署ID
     * @param isActive アクティブフラグ
     * @return 該当する部署のリスト
     */
    List<Department> findByParentIdAndActiveOrderByDisplayOrder(Long parentId, Boolean active);

    /**
     * 部署の存在確認を行います
     * 
     * @param departmentName 部署名
     * @return 存在する場合true
     */
    boolean existsByName(String name);

    /**
     * 指定された親部署の下に子部署が存在するかチェックします
     * 
     * @param parentDepartmentId 親部署ID
     * @return 子部署が存在する場合true
     */
    boolean existsByParentId(Long parentId);
}
