//
//  ViewController.h
//  Boppit
//
//  Created by Emma Lautz on 1/24/15.
//  Copyright (c) 2015 Emma Lautz. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

-(IBAction)playOneButtonPushed:(id)sender;
-(IBAction)playTwoButtonPushed:(id)sender;
-(IBAction)playThreeButtonPushed:(id)sender;
-(IBAction)tryAgainTouched:(id)sender;
-(IBAction)learnButtonPressed:(id)sender;
-(IBAction)playGame:(id)sender;
-(void)swiperight:(UISwipeGestureRecognizer*)gestureRecognizer;
-(void)swipeleft:(UISwipeGestureRecognizer*)gestureRecognizer;
-(void)swipedown:(UISwipeGestureRecognizer*)gestureRecognizer;
-(void)swipeup:(UISwipeGestureRecognizer*)gestureRecognizer;

@end

