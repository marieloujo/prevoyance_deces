<?php

namespace App;

use App\Models\Client;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use HasApiTokens, Notifiable;
    protected $morphClass = 'App\User'; 
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nom','prenom','telephone','adresse','sexe','date_naissance','situation_matrimoniale','prospect', 'actif', 'login', 'commune_id','marchand_id','userable_id', 'userable_type','email', 'password',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function userable(){
        return $this->morphTo();
    }

    public function messages(){
        return $this->hasMany('App\Models\Messages');
    }
    
    public function conversations_user(){
        return $this->hasMany('App\Models\ConversationUser');
    }

    public function conversations(){
        return $this->belongsToMany('App\Models\Conversation','conversation_user','user_id','conversation_id')->using('App\Models\ConversationUser')->withPivot([
            'read',
        ])->withTimestamps();
    }

    public function oauth_client(){
        return $this->hasOne('App\OauthClient','user_id');
    }

    public function commune(){
        return $this->belongsTo('App\Models\Commune');
    }

    public function marchand(){
        return $this->belongsTo('App\Models\Marchand');
    }

    /** 
     * Find the user instance for the given telephone. 
     *  @param string $username 
     *  @return \App\User 
    */
    public function findForPassport($username) {
        return $this->where('login', $username)->first(); 
    }
}
