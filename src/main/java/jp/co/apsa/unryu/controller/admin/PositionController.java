package jp.co.apsa.unryu.controller.admin;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.apsa.unryu.application.service.PositionService;
import jp.co.apsa.unryu.domain.entity.Position;

/**
 * 役職マスタを管理するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    /** 一覧表示 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "役職一覧");
        model.addAttribute("positions", positionService.findAll());
        return "position_list";
    }

    /** 新規作成フォーム */
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("title", "役職作成");
        model.addAttribute("position", new Position());
        return "position_detail";
    }

    /** 編集フォーム */
    @GetMapping("/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("title", "役職編集");
        model.addAttribute("position", positionService.findById(id));
        return "position_detail";
    }

    /** 登録・更新処理 */
    @PostMapping
    public String save(@Valid Position position, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", position.getId() == null ? "役職作成" : "役職編集");
            return "position_detail";
        }
        if (position.getId() == null) {
            positionService.create(position);
        } else {
            positionService.update(position);
        }
        return "redirect:/admin/positions";
    }

    /** 削除処理 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        boolean deleted = positionService.delete(id);
        if (!deleted) {
            model.addAttribute("deleteError", true);
            model.addAttribute("position", positionService.findById(id));
            return "position_detail";
        }
        return "redirect:/admin/positions";
    }
}
