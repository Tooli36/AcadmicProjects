extends Node2D


func toGame():
	var gamescene = load("res://scenes/GameWorld.tscn")
	get_tree().change_scene_to(gamescene)

func _on_start_button_down():
	$animation_sun.play("bird_enter")
	$Control.hide()
	$Logo.hide()
	Globals.current_level=1


func _on_credits_button_down():
	$animation_sun.play("credits")
