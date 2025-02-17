from random import random, choice
from core import Agent
from gym import Env
from gridworld import *
import matplotlib.pyplot as plt


class QLAgent(Agent):

    def __init__(self, env: Env, capacity: int = 20000):
        super(QLAgent, self).__init__(env, capacity)
        self.Q = {}
        self.resetAgent()
        return

    def resetAgent(self):
        self.state = self.env.reset()
        s_name = self._get_state_name(self.state)
        self._assert_state_in_Q(s_name, randomized=False)

    # using simple decaying esilon greedy exploration
    def _curPolicy(self, s, episode_num, use_epsilon):
        epsilon = 1.00 / episode_num
        Q_s = self.Q[s]
        str_act = "unknown"
        rand_value = random()
        action = None
        if use_epsilon and rand_value < epsilon:
            action = self.env.action_space.sample()
        else:
            str_act = max(Q_s, key=Q_s.get)
            action = int(str_act)
        return action

    # choose one aciton based on current policy and state
    def performPolicy(self, s, episode_num, use_epsilon=True):
        return self._curPolicy(s, episode_num, use_epsilon)

    # Q learning
    def QLearning(self, gamma, alpha, max_episode_num):
        # self.Position_t_name, self.reward_t1 = self.observe(env)
        total_time = 0
        num_episode = 1
        time_record = []
        while num_episode <= max_episode_num:
            self.state = str(self.env.reset())
            s0 = self.state
            self.env.render()
            a0 = self.performPolicy(s0, num_episode)
            time_in_episode = 0
            is_done = False
            while not is_done:
                print(self.Q)  # check state and action state value

                s1, r1, is_done, info, total_reward = self.act(a0)
                self.env.render()
                s1 = str(s1)
                self._assert_state_in_Q(s1, randomized=True)
                a1 = self.performPolicy(s1, num_episode, use_epsilon=False)
                old_q = self._get_Q(s0, a0)
                q_prime = self._get_Q(s1, a1)
                td_target = r1 + gamma * q_prime
                new_q = old_q + alpha * (td_target - old_q)
                self._set_Q(s0, a0, new_q)
                s0, a0 = s1, a1
                time_in_episode += 1
            time_record.append(time_in_episode)

            print(self.experience.last)

            total_time += time_in_episode
            num_episode += 1
        self.experience.last.print_detail()

        plt.plot(time_record)
        plt.show()

        return

    def _is_state_in_Q(self, s):
        return self.Q.get(s) is not None

    def _init_state_value(self, s_name, randomized=True):
        if not self._is_state_in_Q(s_name):
            self.Q[s_name] = {}
            for action in range(self.action_space.n):
                default_v = random() / 10 if randomized is True else 0.0
                self.Q[s_name][action] = default_v

    def _assert_state_in_Q(self, s, randomized=True):
        # 　cann't find the state
        if not self._is_state_in_Q(s):
            self._init_state_value(s, randomized)

    def _get_state_name(self, state):
        return str(state)

    def _get_Q(self, s, a):
        self._assert_state_in_Q(s, randomized=True)
        return self.Q[s][a]

    def _set_Q(self, s, a, value):
        self._assert_state_in_Q(s, randomized=True)
        self.Q[s][a] = value


if __name__ == "__main__":
    env = TaskGridWorld_1()
    agent = QLAgent(env, capacity=2000)
    print("Learning...")
    agent.QLearning(gamma=0.5,
                    alpha=0.1,
                    max_episode_num=100)

    print(agent.experience)
