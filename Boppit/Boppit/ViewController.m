//
//  ViewController.m
//  Boppit
//
//  Created by Emma Lautz on 1/24/15.
//  Copyright (c) 2015 Emma Lautz. All rights reserved.
//

#import "ViewController.h"
#import <stdlib.h>

@interface ViewController ()

@property (weak, nonatomic) IBOutlet UILabel * messageLabel;
@property int rando;
@property int command;
@property int score;
@property int timer;
@property int timerHouse;
@property NSTimer * t;
@property NSTimer * tGhost;
@property NSTimer * tHouse;
@property int level;
@property int timerFinal;
@property int ghostTimer;
@property UIImageView *backgroundView;
@property (weak, nonatomic) IBOutlet UILabel * scoreLabel;
@property (weak, nonatomic) IBOutlet UILabel * countdown;
@property (weak, nonatomic) IBOutlet UILabel * scoreTitle;
@property (weak, nonatomic) IBOutlet UILabel * timerTitle;
@property (weak, nonatomic) IBOutlet UILabel * ghostCountdown;
@property (weak, nonatomic) IBOutlet UIButton * Level1;
@property (weak, nonatomic) IBOutlet UIButton* Level2;
@property (weak, nonatomic) IBOutlet UIButton * Level3;
@property (weak, nonatomic) IBOutlet UIButton * learnButton;
@property (weak, nonatomic) IBOutlet UIButton * PassButton;
//@property (weak, nonatomic) IBOutlet UIButton * BackButton;
@property (weak, nonatomic) IBOutlet UIButton * Tap;
@property (weak, nonatomic) IBOutlet UIButton * TryAgainButton;
@property (weak, nonatomic) IBOutlet UIButton * playGameButton;

@end

@implementation ViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    
    UISwipeGestureRecognizer * swiperight=[[UISwipeGestureRecognizer alloc]initWithTarget:self action:@selector(swiperight:)];
    swiperight.direction=UISwipeGestureRecognizerDirectionRight;
    [self.view addGestureRecognizer:swiperight];
    
    UISwipeGestureRecognizer * swipeleft=[[UISwipeGestureRecognizer alloc]initWithTarget:self action:@selector(swipeleft:)];
    swipeleft.direction=UISwipeGestureRecognizerDirectionLeft;
    [self.view addGestureRecognizer:swipeleft];
    
    UISwipeGestureRecognizer * swipeup=[[UISwipeGestureRecognizer alloc]initWithTarget:self action:@selector(swipeup:)];
    swipeup.direction=UISwipeGestureRecognizerDirectionUp;
    [self.view addGestureRecognizer:swipeup];
    
    UISwipeGestureRecognizer * swipedown=[[UISwipeGestureRecognizer alloc]initWithTarget:self action:@selector(swipedown:)];
    swipedown.direction=UISwipeGestureRecognizerDirectionDown;
    [self.view addGestureRecognizer:swipedown];
    _command = 10;
    _score = 0;
    _ghostTimer = 3;
    _timerHouse = 3;
    self.Tap.enabled = NO;
    _messageLabel.text = @"";
    self.TryAgainButton.enabled = NO;
    
    self.Level1.enabled = NO;
    self.Level2.enabled = NO;
    self.Level3.enabled = NO;
    self.playGameButton.enabled = YES;
    
    self.PassButton.enabled = NO;
    self.PassButton.hidden = YES;
    self.Level1.hidden = YES;
    self.Level2.hidden = YES;
    self.Level3.hidden = YES;
    self.TryAgainButton.hidden = YES;
    self.Tap.hidden = YES;
    self.playGameButton.hidden = NO;
    self.learnButton.hidden = NO;
    self.learnButton.enabled = YES;
    self.timer = self.timerFinal = 30;

    //self.BackButton.enabled = NO;
    //self.BackButton.hidden = YES;
    
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"Title.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    
}

-(IBAction)Pass:(id)sender
{
    self.PassButton.enabled = NO;
    self.PassButton.hidden = YES;
    if (_command != 6 && _command != 0)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        [self playButtonPushed:self];
    }

    
}

-(IBAction)playGame:(id)sender
{
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"ChooseLevel1.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    
    _score = 0;
    self.Level1.enabled = YES;
    self.Level2.enabled = YES;
    self.Level3.enabled = YES;
    self.Level1.hidden = NO;
    self.Level2.hidden = NO;
    self.Level3.hidden = NO;
    self.playGameButton.enabled = NO;
    self.playGameButton.hidden = YES;
    self.learnButton.hidden = YES;
    self.learnButton.enabled = NO;
}

-(IBAction)tryAgainTouched:(id)sender
{
    
    _timer = _timerFinal;
    _score = 0;
    self.TryAgainButton.enabled = NO;
    self.TryAgainButton.hidden = YES;
    
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"ChooseLevel1.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    
    self.Level1.enabled = YES;
    self.Level2.enabled = YES;
    self.Level3.enabled = YES;
    self.Level1.hidden = NO;
    self.Level2.hidden = NO;
    self.Level3.hidden = NO;
}

-(IBAction)learnButtonPressed:(id)sender
{
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"Learn.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    
    self.TryAgainButton.enabled = YES;
    self.TryAgainButton.hidden = NO;

}

-(void)swipeleft:(UISwipeGestureRecognizer*)gestureRecognizer
{
    if (_command != 1 && _command != 10)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        if (_score > 0)
        {
            printf("score is greater than 0");
        }
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        //fprint(intString);
        [self playButtonPushed:self];
        
    }
}

- (void)subtractTime {
    // 1
    printf("are we subing?");
    _timer--;
    _countdown.text = [NSString stringWithFormat:@"Time: %i",_timer];
    
    // 2
    if (_timer == 0) {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;

    }
}

-(void)swiperight:(UISwipeGestureRecognizer*)gestureRecognizer
{
    if (_command != 0 && _command != 10)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        [self playButtonPushed:self];
    }
}

-(void)swipedown:(UISwipeGestureRecognizer*)gestureRecognizer
{
    if (_command != 3 && _command != 10)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        [self playButtonPushed:self];
    }
}

-(void)swipeup:(UISwipeGestureRecognizer*)gestureRecognizer
{
    if (_command != 2 && _command != 10)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        [self playButtonPushed:self];
    }
}

- (void)subtractTimeGhost {
    // 1
    _ghostTimer--;
    
    // 2
    if (_ghostTimer == 0) {
        [_tGhost invalidate];
        _t = nil;
        [self playButtonPushed:self];
        
    }
}

-(IBAction)playOneButtonPushed:(id)sender
{
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"1H.png"] drawInRect:self.view.bounds];
    UIImage *image2 = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image2];
    
    _tGhost = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                               target:self
                                             selector:@selector(subtractTimeGhost)
                                             userInfo:nil
                                              repeats:YES];
    _ghostTimer = 5;
    //self.view.backgroundColor = [UIColor whiteColor];
    _score = 0;
    _timer = _timerFinal = 60;
    _level = 1;
    self.Level1.hidden = YES;
    self.Level2.hidden = YES;
    self.Level3.hidden = YES;
    self.Tap.hidden = NO;
    self.Level1.enabled = NO;
    self.Level2.enabled = NO;
    self.Level3.enabled = NO;
    self.Tap.enabled = YES;
    _t = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                          target:self
                                        selector:@selector(subtractTime)
                                        userInfo:nil
                                         repeats:YES];
    //_ghostTimer = 5;

    //[self startCountdownGhost: self ];
}

-(IBAction)playTwoButtonPushed:(id)sender
{
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"2H.png"] drawInRect:self.view.bounds];
    UIImage *image2 = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image2];
    
    _tGhost = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                               target:self
                                             selector:@selector(subtractTimeGhost)
                                             userInfo:nil
                                              repeats:YES];
    _ghostTimer = 5;
    _score = 0;
    _timer = _timerFinal = 40;
    _level = 2;
    //_ghostTimer = 5;
    self.Level1.hidden = YES;
    self.Level2.hidden = YES;
    self.Level3.hidden = YES;
        self.Tap.hidden = NO;
    self.Level1.enabled = NO;
    self.Level2.enabled = NO;
    self.Level3.enabled = NO;
    self.Tap.enabled = YES;
    _t = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                          target:self
                                        selector:@selector(subtractTime)
                                        userInfo:nil
                                         repeats:YES];

}


-(IBAction)playThreeButtonPushed:(id)sender
{

    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"3H.png"] drawInRect:self.view.bounds];
    UIImage *image2 = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image2];
    
    _tGhost = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                               target:self
                                             selector:@selector(subtractTimeGhost)
                                             userInfo:nil
                                              repeats:YES];
    _ghostTimer = 5;
    _score = 0;
    _timer = _timerFinal = 20;
    _level = 3;
    //_ghostTimer = 5;
    self.Level1.hidden = YES;
    self.Level2.hidden = YES;
    self.Level3.hidden = YES;
        self.Tap.hidden = NO;
    self.Tap.hidden = NO;
    self.Level1.enabled = NO;
    self.Level2.enabled = NO;
    self.Level3.enabled = NO;
    self.Tap.enabled = YES;
    
    _t = [NSTimer scheduledTimerWithTimeInterval:1.0f
                                          target:self
                                        selector:@selector(subtractTime)
                                        userInfo:nil
                                         repeats:YES];

}

-(IBAction)Tapped:(id)sender
{
    self.Tap.enabled = NO;
    self.Tap.hidden = YES;
    printf("in tap method");
    if (_command != 5 && _command != 10)
    {
        [_t invalidate];
        _t = nil;
        UIGraphicsBeginImageContext(self.view.frame.size);
        [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        self.view.backgroundColor = [UIColor colorWithPatternImage:image];
        self.TryAgainButton.hidden = NO;
        self.TryAgainButton.enabled = YES;
    }
    else{
        _score++;
        NSString *intString = [NSString stringWithFormat:@"%d", _score];
        self.scoreLabel.text = intString;
        [self playButtonPushed:self];
    }
}

-(void)playButtonPushed:(id)sender
{
    _scoreTitle.text = @"Your score: ";
   // _timerTitle.text = @"Timer: ";
    self.TryAgainButton.hidden = YES;
    self.TryAgainButton.enabled = NO;
    self.Level1.hidden = YES;
    self.Level2.hidden = YES;
    self.Level3.hidden = YES;
    self.Level1.enabled = NO;
    self.Level2.enabled = NO;
    self.Level3.enabled = NO;
   // _rando = arc4random_uniform(6);
    //printf("This is a neat command!\n");
    [self.view setBackgroundColor:[UIColor redColor] ];
    //printf("After setting color");
    if(_score >25)
    {
        [_t invalidate];
        _t = nil;
        _score = 0;
        if (_level == 1)
        {
            [self OneComplete: self];
        }
        else if (_level == 2)
        {
            [self TwoComplete: self];
        }
        else{
            [self ThreeComplete: self];
        }
    }
    else
    {
        while(true)
        {
            _rando = arc4random_uniform(7);
            //printf("in while loop");
            if (_rando == 0)
            {
            
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"SwipeRight.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                self.messageLabel.text = @"";
                _command = 0;
                //[self startCountdown:self];
            }
            else if (_rando == 1)
            {
            
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"SwipeLeft.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                //[self startCountdown:self];
                self.messageLabel.text = @"";
                _command = 1;
            }
            else if (_rando == 2)
            {
            
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"SwipeUp.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                //[self startCountdown:self];
                self.messageLabel.text = @"";
                _command = 2;
            }
            else if (_rando == 3)
            {
            
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"SwipeDown.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                //[self startCountdown:self];
                self.messageLabel.text = @"";
                _command = 3;
            }
            else if (_rando == 4)
            {
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"ShakeScreen.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                //[self startCountdown:self];
                self.messageLabel.text = @"";
                _command = 4;
            }
            else if (_rando == 5){
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"TapScreen.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
            
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            
                //[self startCountdown:self];
                self.Tap.enabled = YES;
                self.Tap.hidden = NO;
                self.messageLabel.text = @"";
                _command = 5;
            }
            else{
                UIGraphicsBeginImageContext(self.view.frame.size);
                [[UIImage imageNamed:@"handit.png"] drawInRect:self.view.bounds];
                UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
                UIGraphicsEndImageContext();
                
                self.view.backgroundColor = [UIColor colorWithPatternImage:image];
                
                //[self startCountdown:self];
                self.Tap.enabled = NO;
                self.Tap.hidden = YES;
                self.PassButton.enabled = YES;
                self.PassButton.hidden = NO;
                self.messageLabel.text = @"";
                _command = 6;

            }
            break;
        }
        
    }
    
}

-(void)OneComplete: sender
{
    [_t invalidate];
    _t = nil;
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"1H-R.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    self.TryAgainButton.enabled = YES;
    self.TryAgainButton.hidden = NO;
}

-(void)TwoComplete: sender
{
    [_t invalidate];
    _t = nil;
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"2H-R.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    self.TryAgainButton.enabled = YES;
    self.TryAgainButton.hidden = NO;
}

-(void)ThreeComplete: sender
{
    [_t invalidate];
    _t = nil;
    UIGraphicsBeginImageContext(self.view.frame.size);
    [[UIImage imageNamed:@"3H-R.png"] drawInRect:self.view.bounds];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:image];
    self.TryAgainButton.enabled = YES;
    self.TryAgainButton.hidden = NO;
}

- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event {
    printf("in shake event");
    if (motion == UIEventSubtypeMotionShake)
    {
        if (_command != 4 && _command != 10)
        {
            [_t invalidate];
            _t = nil;
            UIGraphicsBeginImageContext(self.view.frame.size);
            [[UIImage imageNamed:@"LosingScreen.png"] drawInRect:self.view.bounds];
            UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
            UIGraphicsEndImageContext();
            
            self.view.backgroundColor = [UIColor colorWithPatternImage:image];
            self.TryAgainButton.hidden = NO;
            self.TryAgainButton.enabled = YES;
        }
        else{
            _score++;
            NSString *intString = [NSString stringWithFormat:@"%d", _score];
            self.scoreLabel.text = intString;
            [self playButtonPushed:self];
        }
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



@end
