extends Node

var random = RandomNumberGenerator.new()
var marker_created=false
var root_isout=false
var marker
var drag_speed = 50
var return_speed = 30

var health_points : float = 100
var health_points_max = 100
var water_points : float = 100
var water_points_max = 100
var growth_points = 0

var lose_plastic = 20
var lose_can = 10
var lose_ocan = 10
var lose_bag = 20
var lose_worm = 15
var lose_bug = 25


var current_level=1
var lose_water_lv1 = .05
var lose_dry_lv1 = .02
var lose_water_lv2 = .07
var lose_dry_lv2 = .025
var lose_water_lv3 = .08
var lose_dry_lv3 = .03
var min_lv1 = 350
var min_lv2 = 700
var min_lv3 = 1000
var min_lv4 = 1450
var min_lv5 = 1800

var watertimer_lv1 = 3
var watertimer_lv2 = 5
var watertimer_lv3 = 9
var watertimer_lv4 = 14
var watertimer_lv5 = 18
var waterdropsmall = 20
var waterdropmed = 45
var waterdropbig = 65

var water_plastic_lv1 = 30
var water_plastic_lv2 = 25
var water_plastic_lv3 = 20
var water_plastic_lv4 = 15
var water_plastic_lv5 = 10

var can_lv1 = 30
var can_lv2 = 25
var can_lv3 = 20
var can_lv4 = 15
var can_lv5 = 10

var ocan_lv1 = 30
var ocan_lv2 = 25
var ocan_lv3 = 20
var ocan_lv4 = 15
var ocan_lv5 = 10

var bag_lv1 = 30
var bag_lv2 = 25
var bag_lv3 = 20
var bag_lv4 = 15
var bag_lv5 = 10

var bug_worm_lv1 = 30
var bug_worm_lv2 = 25
var bug_worm_lv3 = 20
var bug_worm_lv4 = 15
var bug_worm_lv5 = 10
